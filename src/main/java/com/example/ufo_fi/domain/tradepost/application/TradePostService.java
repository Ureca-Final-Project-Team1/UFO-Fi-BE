package com.example.ufo_fi.domain.tradepost.application;

import com.example.ufo_fi.domain.notification.event.CreatedPostEvent;
import com.example.ufo_fi.domain.notification.event.TradeCompletedEvent;
import com.example.ufo_fi.domain.tradepost.domain.TradeHistory;
import com.example.ufo_fi.domain.tradepost.domain.TradePost;
import com.example.ufo_fi.domain.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.domain.tradepost.domain.TradeType;
import com.example.ufo_fi.domain.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.domain.tradepost.infrastructure.TradeHistoryRepository;
import com.example.ufo_fi.domain.tradepost.infrastructure.TradePostRepository;
import com.example.ufo_fi.domain.tradepost.presentation.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.domain.tradepost.presentation.dto.request.TradePostConfirmBulkReq;
import com.example.ufo_fi.domain.tradepost.presentation.dto.request.TradePostCreateReq;
import com.example.ufo_fi.domain.tradepost.presentation.dto.request.TradePostPurchaseReq;
import com.example.ufo_fi.domain.tradepost.presentation.dto.request.TradePostQueryReq;
import com.example.ufo_fi.domain.tradepost.presentation.dto.request.TradePostUpdateReq;
import com.example.ufo_fi.domain.tradepost.presentation.dto.response.PurchaseHistoriesRes;
import com.example.ufo_fi.domain.tradepost.presentation.dto.response.PurchaseHistoryRes;
import com.example.ufo_fi.domain.tradepost.presentation.dto.response.SaleHistoriesRes;
import com.example.ufo_fi.domain.tradepost.presentation.dto.response.TradePostBulkPurchaseConfirmRes;
import com.example.ufo_fi.domain.tradepost.presentation.dto.response.TradePostBulkPurchaseRes;
import com.example.ufo_fi.domain.tradepost.presentation.dto.response.TradePostCommonRes;
import com.example.ufo_fi.domain.tradepost.presentation.dto.response.TradePostFailPurchaseRes;
import com.example.ufo_fi.domain.tradepost.presentation.dto.response.TradePostListRes;
import com.example.ufo_fi.domain.tradepost.presentation.dto.response.TradePostPurchaseDetailRes;
import com.example.ufo_fi.domain.tradepost.presentation.dto.response.TradePostPurchaseRes;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.entity.UserPlan;
import com.example.ufo_fi.domain.user.service.UserManager;
import com.example.ufo_fi.domain.user.service.UserPlanManager;
import com.example.ufo_fi.global.exception.GlobalException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradePostService {

    private final UserManager userManager;
    private final UserPlanManager userPlanManager;
    private final TradePostManager tradePostManager;
    private final TradePostMapper tradePostMapper;
    private final TradePostRepository tradePostRepository;
    private final TradeHistoryRepository tradeHistoryRepository;
    private final ApplicationEventPublisher publisher;


    @Transactional
    public TradePostCommonRes createTradePost(TradePostCreateReq request, Long userId) {

        tradePostManager.validateBannedWord(request.getTitle());

        User user = userManager.validateUserExistence(userId);

        UserPlan userPlan = userPlanManager.validateUserPlanExistence(user);

        userPlan.validateAndSubtractForSale(request.getSellDataAmount());

        TradePost tradePost = tradePostMapper.toTradePost(request, TradePostStatus.SELLING,
            user, userPlan);

        TradePost savedTradePost = tradePostManager.saveTradePost(tradePost);

        publisher.publishEvent(
            new CreatedPostEvent(
                user.getId(),
                savedTradePost.getId(),
                savedTradePost.getCarrier(),
                savedTradePost.getTotalZet(),
                savedTradePost.getSellMobileDataCapacityGb()
            )
        );

        return TradePostCommonRes.from(savedTradePost.getId());
    }


    /**
     * 게시물 조회(cursor 기반 최신순 정렬)
     */
    public TradePostListRes readTradePostList(TradePostQueryReq request, Long userId) {

        int pageSize = 0;
        if (request.getSize() != null && request.getSize() > 0) {
            pageSize = request.getSize();
        }
        pageSize = 20;

        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<TradePost> posts = tradePostManager.findPostsByCondition(request, pageable
        );

        tradePostManager.validatePostsExistence(posts);

        return TradePostListRes.of(posts);
    }

    /**
     * 게시물 수정
     */
    @Transactional
    public TradePostCommonRes updateTradePost(Long postId, TradePostUpdateReq request,
        Long userId) {

        tradePostManager.validateBannedWord(request.getTitle());

        User user = userManager.validateUserExistence(userId);

        TradePost tradePost = tradePostManager.validateAndFindByIdWithLock(postId);

        tradePost.verifyOwner(user);

        if (request.getSellMobileDataCapacityGb() != null) {
            UserPlan userPlan = userPlanManager.validateUserPlanExistence(user);

            int originalDataAmount = tradePost.getSellMobileDataCapacityGb();
            int newDataAmount = request.getSellMobileDataCapacityGb();

            userPlan.updateSellableDataAmount(originalDataAmount, newDataAmount);
        }

        tradePost.update(request);

        return TradePostCommonRes.from(tradePost.getId());
    }

    /**
     * 게시물 삭제
     */
    @Transactional
    public TradePostCommonRes deleteTradePost(Long postId, Long userId) {

        User user = userManager.validateUserExistence(userId);

        TradePost tradePost = tradePostManager.validateAndFindById(postId);
        tradePost.verifyOwner(user);

        int dataToRestore = tradePost.delete();
        UserPlan userPlan = userPlanManager.validateUserPlanExistence(user);
        userPlan.increaseSellableDataAmount(dataToRestore);

        return TradePostCommonRes.from(tradePost.getId());
    }

    /**
     * 1. 일괄 구매 조회 로직
     */
    @Transactional(readOnly = true)
    public TradePostBulkPurchaseRes readBulkPurchase(TradePostBulkPurchaseReq request,
        Long userId) {

        User user = userManager.validateUserExistence(userId);

        UserPlan userPlan = userPlanManager.validateUserPlanExistence(user);

        List<TradePost> candidates = tradePostRepository.findCheapestCandidates(request,
            userPlan.getPlan().getCarrier(),
            userPlan.getPlan().getMobileDataType(), userId);

        List<TradePost> recommendationList = new ArrayList<>();

        int cumulativeGb = 0;
        final int desiredGb = request.getDesiredGb();
        final int unitPrice = request.getUnitPerZet();

        for (TradePost post : candidates) {
            if (cumulativeGb + post.getSellMobileDataCapacityGb() <= desiredGb
                && post.getZetPerUnit() <= unitPrice) {

                recommendationList.add(post);
                cumulativeGb += post.getSellMobileDataCapacityGb();
            }
        }
        if (recommendationList.isEmpty()) {
            throw new GlobalException(TradePostErrorCode.NO_RECOMMENDATION_FOUND);
        }

        return TradePostBulkPurchaseRes.from(recommendationList);
    }

    /**
     * 일괄 구매 구매 요청
     */
    @Transactional
    public TradePostBulkPurchaseConfirmRes bulkPurchase(TradePostConfirmBulkReq request,
        Long userId) {

        List<Long> postIds = request.getPostIds();

        if (postIds == null || postIds.isEmpty()) {
            throw new GlobalException(TradePostErrorCode.POST_NOT_FOUND);
        }

        User buyer = userManager.validateUserExistence(userId);
        UserPlan buyerPlan = userPlanManager.validateUserPlanExistence(buyer);

        List<TradePost> successfulPurchases = new ArrayList<>();
        List<TradePostFailPurchaseRes> failedPurchases = new ArrayList<>();
        List<TradeHistory> historiesToSave = new ArrayList<>();

        for (Long postId : postIds) {
            Optional<TradePost> optPost = tradePostRepository.findByIdWithLock(postId);

            if (optPost.isEmpty()) {
                failedPurchases.add(
                    TradePostFailPurchaseRes.ofNotFound(postId, "존재하지 않는 게시물 입니다."));
                continue;
            }

            TradePost lockedPost = optPost.get();

            if (!lockedPost.getCarrier().equals(buyerPlan.getPlan().getCarrier())) {
                failedPurchases.add(TradePostFailPurchaseRes.of(lockedPost, "통신사가 다릅니다."));
                continue;
            }

            if (!lockedPost.getMobileDataType().equals(buyerPlan.getPlan().getMobileDataType())) {
                failedPurchases.add(TradePostFailPurchaseRes.of(lockedPost, "데이터 타입이 다릅니다."));
                continue;
            }

            if (lockedPost.getTradePostStatus().equals(TradePostStatus.DELETED)) {
                failedPurchases.add(TradePostFailPurchaseRes.of(lockedPost, "삭제된 상품입니다."));
                continue;
            }

            if (lockedPost.getTradePostStatus().equals(TradePostStatus.REPORTED)) {
                failedPurchases.add(TradePostFailPurchaseRes.of(lockedPost, "신고된 상품입니다."));
                continue;
            }

            if (lockedPost.getTradePostStatus().equals(TradePostStatus.EXPIRED)) {
                failedPurchases.add(TradePostFailPurchaseRes.of(lockedPost, "만료된 상품입니다."));
                continue;
            }

            if (lockedPost.getTradePostStatus().equals(TradePostStatus.SOLD_OUT)) {
                failedPurchases.add(TradePostFailPurchaseRes.of(lockedPost, "판매된 상품입니다."));
                continue;
            }

            if (lockedPost.getUser().getId().equals(userId)) {
                failedPurchases.add(TradePostFailPurchaseRes.of(lockedPost, "자신의 상품은 구매할 수 없습니다."));
                continue;
            }

            successfulPurchases.add(lockedPost);

        }

        int totalCost = successfulPurchases.stream().mapToInt(TradePost::getTotalZet).sum();

        if (buyer.getZetAsset() < totalCost) {

            throw new GlobalException(TradePostErrorCode.ZET_LACK);
        }

        for (TradePost postToBuy : successfulPurchases) {
            User seller = postToBuy.getUser();
            seller.increaseZetAsset(postToBuy.getTotalZet());
            postToBuy.updateStatusSoldOut();

            historiesToSave.add(TradeHistory.toPurchase(postToBuy, buyer));
            historiesToSave.add(TradeHistory.toSale(postToBuy, seller));

            publisher.publishEvent(new TradeCompletedEvent(seller.getId()));
        }

        if (!historiesToSave.isEmpty()) {
            tradeHistoryRepository.saveAll(historiesToSave);
        }

        int totalGb = successfulPurchases.stream().mapToInt(TradePost::getSellMobileDataCapacityGb)
            .sum();

        buyer.decreaseZetAsset(totalCost);
        buyerPlan.increasePurchaseAmount(totalGb);

        return TradePostBulkPurchaseConfirmRes.of(successfulPurchases, failedPurchases);
    }


    /**
     * TradePostPurchaseController
     * 1. 거래 게시글을 조회하고 유효성을 검사
     * 2. 구매자와 판매자 동일 여부를 체크
     * 3. 구매자의 자산이 충분한가?
     * 4. 자산 차감/판매자에게 지급 예
     */
    @Transactional
    public TradePostPurchaseRes purchase(Long userId, TradePostPurchaseReq purchaseReq) {
        TradePost tradePost = tradePostRepository.findById(purchaseReq.getPostId())
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.NO_TRADE_POST_FOUND));

        if (tradePost.getTradePostStatus().equals(TradePostStatus.SOLD_OUT)) {
            throw new GlobalException(TradePostErrorCode.ALREADY_SOLDOUT);
        }

        if (tradePost.getTradePostStatus().equals(TradePostStatus.DELETED)) {
            throw new GlobalException(TradePostErrorCode.ALREADY_DELETE);
        }

        if (tradePost.getTradePostStatus().equals(TradePostStatus.REPORTED)) {
            throw new GlobalException(TradePostErrorCode.ALREADY_REPORTED);
        }

        User buyer = userManager.validateUserExistence(userId);

        UserPlan buyerPlan = userPlanManager.validateUserPlanExistence(buyer);

        User seller = tradePost.getUser();

        if (Objects.equals(buyer.getId(), seller.getId())) {
            throw new GlobalException(TradePostErrorCode.CANT_PURCHASE_MYSELF);
        }

        if (buyer.getZetAsset() < tradePost.getTotalZet()) {
            throw new GlobalException(TradePostErrorCode.ZET_LACK);
        }

        buyer.decreaseZetAsset(tradePost.getTotalZet());
        buyerPlan.increasePurchaseAmount(tradePost.getSellMobileDataCapacityGb());
        seller.increaseZetAsset(tradePost.getTotalZet());
        tradePost.updateStatusSoldOut();

        //saveHistories(); 내역 저장 로직 후에 추가
        saveTradeHistories(tradePost, buyer, seller);

        // 거래 완료 이벤트 발행
        publisher.publishEvent(new TradeCompletedEvent(seller.getId()));

        return TradePostPurchaseRes.from(buyer);
    }

    /**
     * MyPageTradeHistoryController
     * 1. 상태가 SALE인 거래 내역을 userId로 찾아옵니다.
     * 2. DTO 매핑하고 리턴합니다.
     */
    public SaleHistoriesRes readSaleHistories(Long userId) {
        List<TradeHistory> tradeHistories = tradeHistoryRepository.findByUserIdAndStatus(
            TradeType.SALE, userId
        );

        return SaleHistoriesRes.from(tradeHistories);
    }

    /**
     * MyPageTradeHistoryController
     * 1. 상태가 PURCHASE인 거래 내역을 userId로 찾아옵니다.
     * 2. DTO 매핑하고 리턴합니다.
     */
    public PurchaseHistoriesRes readPurchaseHistories(Long userId) {
        List<TradeHistory> tradeHistories = tradeHistoryRepository.findByUserIdAndStatus(
            TradeType.PURCHASE, userId
        );

        return PurchaseHistoriesRes.from(tradeHistories);
    }

    /**
     * MyPageTradeHistoryController
     * 1. 상태가 PURCHASE인 거래 내역을 purchaseHistoryId로 찾아옵니다.
     * 2. DTO 매핑하고 리턴합니다.
     */
    public PurchaseHistoryRes readPurchaseHistory(Long purchaseHistoryId) {
        TradeHistory tradeHistory = tradeHistoryRepository.findByPurchaseHistoryIdAndStatus(
            TradeType.PURCHASE, purchaseHistoryId
        );
        if (tradeHistory == null) {
            throw new GlobalException(TradePostErrorCode.NO_TRADE_POST_FOUND);
        }

        return PurchaseHistoryRes.from(tradeHistory);
    }

    private void saveTradeHistories(TradePost tradePost, User buyer, User seller) {

        TradeHistory purchaseHistory = TradeHistory.toPurchase(tradePost, buyer);
        TradeHistory saleHistory = TradeHistory.toSale(tradePost, seller);

        tradeHistoryRepository.saveAll(List.of(purchaseHistory, saleHistory));
    }


    public TradePostPurchaseDetailRes readTradePost(Long postId) {
        TradePost tradePost = tradePostManager.validateAndFindById(postId);
        return TradePostPurchaseDetailRes.of(tradePost);
    }
}
