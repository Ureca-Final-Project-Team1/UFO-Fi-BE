package com.example.ufo_fi.v2.order.application;

import com.example.ufo_fi.domain.notification.event.TradeCompletedEvent;
import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.order.domain.TradeHistory;
import com.example.ufo_fi.v2.order.domain.TradeHistoryManager;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostManager;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostConfirmBulkReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostPurchaseReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.PurchaseHistoriesRes;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.PurchaseHistoryRes;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.SaleHistoriesRes;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostBulkPurchaseConfirmRes;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostFailPurchaseRes;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostPurchaseRes;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import com.example.ufo_fi.v2.userplan.domain.UserPlanManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserManager userManager;
    private final UserPlanManager userPlanManager;
    private final TradeHistoryManager tradeHistoryManager;
    private final TradePostManager tradePostManager;
    private final OrderMapper orderMapper;
    private final ApplicationEventPublisher publisher;

    /**
     * MyPageTradeHistoryController
     * 1. 상태가 SALE인 거래 내역을 userId로 찾아옵니다.
     * 2. DTO 매핑하고 리턴합니다.
     */
    public SaleHistoriesRes readSaleHistories(Long userId) {

        List<TradeHistory> tradeHistories = tradeHistoryManager.findByIdAndStatus(userId);

        return orderMapper.toSaleHistoriesRes(tradeHistories);
    }

    /**
     * MyPageTradeHistoryController
     * 1. 상태가 PURCHASE인 거래 내역을 userId로 찾아옵니다.
     * 2. DTO 매핑하고 리턴합니다.
     */
    public PurchaseHistoriesRes readPurchaseHistories(Long userId) {

        List<TradeHistory> tradeHistories = tradeHistoryManager.findByIdAndStatus(userId);

        return orderMapper.toPurchaseHistoriesRes(tradeHistories);
    }

    /**
     * MyPageTradeHistoryController
     * 1. 상태가 PURCHASE인 거래 내역을 purchaseHistoryId로 찾아옵니다.
     * 2. DTO 매핑하고 리턴합니다.
     */
    public PurchaseHistoryRes readPurchaseHistory(Long purchaseHistoryId) {

        TradeHistory tradeHistory = tradeHistoryManager.findValidatePurchaseHistoryById(
            purchaseHistoryId);

        return PurchaseHistoryRes.from(tradeHistory);
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

        User buyer = userManager.validateUserExistence(userId);
        UserPlan buyerPlan = userPlanManager.validateUserPlanExistence(buyer);
        TradePost tradePost = tradePostManager.validateAndFindById(purchaseReq.getPostId());
        userManager.findById(tradePost.getUser().getId());

        tradePost.validatePurchase(buyer);

        buyerPlan.increasePurchaseAmount(tradePost.getSellMobileDataCapacityGb());
        saveTradeHistories(tradePost, buyer, tradePost.getUser());

        // 거래 완료 이벤트 발행
        publisher.publishEvent(new TradeCompletedEvent(tradePost.getUser().getId()));

        return orderMapper.toTradePostPurchaseRes(buyer);
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
            Optional<TradePost> optPost = Optional.ofNullable(
                tradePostManager.validateAndFindByIdWithLock(postId));

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

        buyer.purchasePosts(successfulPurchases);

        for (TradePost postToBuy : successfulPurchases) {
            User seller = postToBuy.getUser();
            seller.increaseZetAsset(postToBuy.getTotalZet());
            postToBuy.updateStatusSoldOut();

            historiesToSave.add(orderMapper.toPurchaseHistories(postToBuy, buyer));
            historiesToSave.add(orderMapper.toSaleHistories(postToBuy, seller));

            publisher.publishEvent(new TradeCompletedEvent(seller.getId()));
        }

        if (!historiesToSave.isEmpty()) {
            tradeHistoryManager.saveBulkBothHistory(historiesToSave);
        }

        int totalGb = successfulPurchases.stream().mapToInt(TradePost::getSellMobileDataCapacityGb)
            .sum();
        buyerPlan.increasePurchaseAmount(totalGb);

        return orderMapper.toTradePostBulkPurchaseConfirmRes(successfulPurchases, failedPurchases);
    }

    private void saveTradeHistories(TradePost tradePost, User buyer, User seller) {

        TradeHistory purchaseHistory = orderMapper.toPurchaseHistories(tradePost, buyer);
        TradeHistory saleHistory = orderMapper.toSaleHistories(tradePost, seller);

        tradeHistoryManager.saveBothHistory(purchaseHistory, saleHistory);
    }
}
