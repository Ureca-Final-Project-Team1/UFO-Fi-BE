package com.example.ufo_fi.domain.tradepost.service;

import com.example.ufo_fi.domain.bannedword.filter.BannedWordFilter;
import com.example.ufo_fi.domain.notification.event.CreatedPostEvent;
import com.example.ufo_fi.domain.notification.event.TradeCompletedEvent;
import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.report.entity.Report;
import com.example.ufo_fi.domain.report.repository.ReportRepository;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostCreateReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostPurchaseReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostQueryReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostReportReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostUpdateReq;
import com.example.ufo_fi.domain.tradepost.dto.response.PurchaseHistoriesRes;
import com.example.ufo_fi.domain.tradepost.dto.response.PurchaseHistoryRes;
import com.example.ufo_fi.domain.tradepost.dto.response.SaleHistoriesRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostBulkPurchaseRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostCommonRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostListRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostPurchaseRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostReportRes;
import com.example.ufo_fi.domain.tradepost.entity.TradeHistory;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import com.example.ufo_fi.domain.tradepost.entity.TradePostStatus;
import com.example.ufo_fi.domain.tradepost.entity.TradeType;
import com.example.ufo_fi.domain.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.domain.tradepost.repository.TradeHistoryRepository;
import com.example.ufo_fi.domain.tradepost.repository.TradePostRepository;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.entity.UserAccount;
import com.example.ufo_fi.domain.user.entity.UserPlan;
import com.example.ufo_fi.domain.user.repository.UserAccountRepository;
import com.example.ufo_fi.domain.user.repository.UserPlanRepository;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    private final UserRepository userRepository;
    private final BannedWordFilter bannedWordFilter;
    private final ReportRepository reportRepository;
    private final UserPlanRepository userPlanRepository;
    private final TradePostRepository tradePostRepository;
    private final UserAccountRepository userAccountRepository;
    private final TradeHistoryRepository tradeHistoryRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public TradePostCommonRes createTradePost(TradePostCreateReq request, Long userId) {

        User user = userRepository.findUserWithUserPlanAndUserAccountWithPessimisticLock(userId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_NOT_FOUND));

        UserPlan userPlan = userPlanRepository.findByUser(user);
        if (userPlan == null) {
            throw new GlobalException(TradePostErrorCode.USER_PLAN_NOT_FOUND);
        }

        Plan plan = userPlan.getPlan();
        if (plan == null) {
            throw new GlobalException(TradePostErrorCode.PLAN_NOT_FOUND);
        }

        UserAccount userAccount = userAccountRepository.findByUser(user);
        if (userAccount == null) {
            throw new GlobalException(TradePostErrorCode.ACCOUNT_NOT_REGISTERED);
        }

        int userAvailableData = userPlan.getSellableDataAmount();
        int requestSellData = request.getSellDataAmount();
        if (requestSellData > userAvailableData) {
            throw new GlobalException(TradePostErrorCode.EXCEED_SELL_CAPACITY);
        }

        TradePost tradePost = TradePost.of(request, TradePostStatus.SELLING, user, userPlan);

        validateBannedWord(request.getTitle());

        userPlan.subtractSellableDataAmount(requestSellData);
        tradePost.calculateTotalPrice();// total 가격 저장
        TradePost savedTradePost = tradePostRepository.save(tradePost);

        // 판매 게시물 생성 이벤트 발생
        publisher.publishEvent(
            new CreatedPostEvent(
                    user.getId(),
                    savedTradePost.getId(),
                    savedTradePost.getCarrier(),
                    savedTradePost.getTotalZet(),
                    savedTradePost.getSellMobileDataCapacityGb()
            )
        );

        return new TradePostCommonRes(savedTradePost.getId());
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

        Slice<TradePost> posts = tradePostRepository.findPostsByConditions(
                request,
                pageable
        );

        validatePostsExistence(posts);

        return TradePostListRes.of(posts);
    }

    /**
     * 게시물 수정
     */
    @Transactional
    public TradePostCommonRes updateTradePost(Long postId, TradePostUpdateReq request,
        Long userId) {

        User user = getUser(userId);

        UserPlan userPlan = userPlanRepository.findByUser(user);

        TradePost tradePost = tradePostRepository.findByIdWithLock(postId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.TRADE_POST_NOT_FOUND));

        tradePost.verifyOwner(tradePost, user);

        if (request.getSellMobileDataCapacityGb() != null) {

            int originalDataAmount = tradePost.getSellMobileDataCapacityGb();
            userPlan.increaseSellableDataAmount(originalDataAmount);

            int newDataAmount = request.getSellMobileDataCapacityGb();

            if (newDataAmount > userPlan.getSellableDataAmount()) {
                throw new GlobalException(TradePostErrorCode.EXCEED_SELL_CAPACITY);
            }

            userPlan.subtractSellableDataAmount(newDataAmount);
        }

        tradePost.calculateTotalPrice();
        tradePost.update(request);

        return new TradePostCommonRes(tradePost.getId());
    }

    /**
     * 게시물 삭제
     */
    @Transactional
    public TradePostCommonRes deleteTradePost(Long postId, Long userId) {

        User user = getUser(userId);

        UserPlan userPlan = userPlanRepository.findByUser(user);

        TradePost tradePost = tradePostRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.TRADE_POST_NOT_FOUND));

        tradePost.verifyOwner(tradePost, user);

        if (!tradePost.getTradePostStatus().equals(TradePostStatus.SELLING)) {

            throw new GlobalException(TradePostErrorCode.CANNOT_DELETE_NOT_SELLING_POST);
        }

        int dataToRestore = tradePost.getSellMobileDataCapacityGb();
        userPlan.increaseSellableDataAmount(dataToRestore);

        tradePost.softDeleteAndStatusDelete();

        return new TradePostCommonRes(tradePost.getId());
    }

    /**
     * 1. 일괄 구매 조회 로직
     */
    @Transactional(readOnly = true)
    public TradePostBulkPurchaseRes readBulkPurchase(TradePostBulkPurchaseReq request,
        Long userId) {

        User user = getUser(userId);

        UserPlan userPlan = userPlanRepository.findByUser(user);

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

    private User getUser(Long userId) {

        return userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_NOT_FOUND));
    }

    private void validatePostsExistence(Slice<TradePost> posts) {

        if (posts.isEmpty()) {

            throw new GlobalException(TradePostErrorCode.NO_TRADE_POST_FOUND);
        }
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

        User buyer = userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.CANT_PURCHASE_MYSELF));

        User seller = tradePost.getUser();

        if (Objects.equals(buyer.getId(), seller.getId())) {
            throw new GlobalException(TradePostErrorCode.CANT_PURCHASE_MYSELF);
        }

        if (buyer.getZetAsset() < tradePost.getTotalZet()) {
            throw new GlobalException(TradePostErrorCode.ZET_LACK);
        }

        buyer.decreaseZetAsset(tradePost.getTotalZet());
        seller.increaseZetAsset(tradePost.getTotalZet());
        //buyer.increaseSellableDataAmount(tradePost.getSellMobileDataCapacityGb());
        tradePost.updateStatusSoldOut();

        //sellable Data 증가
        //saveHistories(); 내역 저장 로직 후에 추가

        // 거래 완료 이벤트 발행
        publisher.publishEvent(new TradeCompletedEvent(seller.getId()));

        return TradePostPurchaseRes.from(buyer);
    }

    /**
     * 게시물 신고 로직
     */
    @Transactional
    public TradePostReportRes createReport(Long userId, Long postId,
        TradePostReportReq tradePostReportReq) {
        TradePost tradePost = tradePostRepository.findTradePostWithReports(postId);

        User reportingUser = userRepository.getReferenceById(userId);
        User reportedUser = userRepository.getReferenceById(
            tradePostReportReq.getPostOwnerUserId()
        );

        Report report = Report.of(
            reportingUser,
            reportedUser,
            tradePost,
            tradePostReportReq
        );

        reportRepository.save(report);

        tradePost.addReport(report);

        int reportCount = tradePost.getReports().size();
        if (reportCount >= 3) {
            tradePost.updateStatusReported();
        }

        return TradePostReportRes.of(report, tradePost, reportCount);
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

    private void validateBannedWord(String content) {
        bannedWordFilter.filter(content);
    }
}
