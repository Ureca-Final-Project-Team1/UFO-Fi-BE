package com.example.ufo_fi.domain.tradepost.service;

import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostCreateReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostQueryReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostUpdateReq;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostBulkPurchaseRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostCommonRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostListRes;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import com.example.ufo_fi.domain.tradepost.entity.TradePostStatus;
import com.example.ufo_fi.domain.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.domain.tradepost.repository.TradePostRepository;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.entity.UserAccount;
import com.example.ufo_fi.domain.user.entity.UserPlan;
import com.example.ufo_fi.domain.user.repository.UserAccountRepository;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradePostService {

    private final UserRepository userRepository;
    private final TradePostRepository tradePostRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional
    public TradePostCommonRes createTradePost(TradePostCreateReq request, Long userId) {

        User user = userRepository.findUserWithUserPlanAndUserAccountWithPessimisticLock(userId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_NOT_FOUND));

        UserPlan userPlan = user.getUserPlan();
        if (userPlan == null) {
            throw new GlobalException(TradePostErrorCode.USER_PLAN_NOT_FOUND);
        }

        Plan plan = userPlan.getPlan();
        if (plan == null) {
            throw new GlobalException(TradePostErrorCode.PLAN_NOT_FOUND);
        }

        UserAccount userAccount = user.getUserAccount();
        if (userAccount == null) {
            throw new GlobalException(TradePostErrorCode.ACCOUNT_NOT_REGISTERED);
        }

        int userAvailableData = userPlan.getSellableDataAmount();
        int requestSellData = request.getSellDataAmount();
        if (requestSellData > userAvailableData) {
            throw new GlobalException(TradePostErrorCode.EXCEED_SELL_CAPACITY);
        }

        TradePost tradePost = TradePost.of(request, TradePostStatus.SELLING, user);
        userPlan.subtractSellableDataAmount(requestSellData);
        tradePost.calculateTotalPrice();// total 가격 저장
        TradePost savedTradePost = tradePostRepository.save(tradePost);
        return new TradePostCommonRes(savedTradePost.getId());
    }


    /**
     * 게시물 조회(cursor 기반 최신순 정렬)
     */
    public TradePostListRes readTradePostList(TradePostQueryReq request, Long userId) {

        int pageSize =
            (request.getSize() != null && request.getSize() > 0) ? request.getSize() : 20;

        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<TradePost> posts = tradePostRepository.findPostsByConditions(
            request, pageable);

        validatePostsExistence(posts);

        return TradePostListRes.of(posts);
    }

    /**
     * 게시물 필터링(cursor 기반 유저가 원하는대로 조회)
     */
    @Transactional
    public TradePostListRes readFilterList(TradePostQueryReq request, Long userId) {
        int pageSize =
            (request.getSize() != null && request.getSize() > 0) ? request.getSize() : 20;

        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<TradePost> posts = tradePostRepository.findPostsByConditions(request, pageable);

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

        TradePost tradePost = tradePostRepository.findByIdWithLock(postId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.TRADE_POST_NOT_FOUND));

        tradePost.verifyOwner(tradePost, user);

        if (request.getSellMobileDataCapacityGb() != null) {

            int originalDataAmount = tradePost.getSellMobileDataCapacityGb();
            user.getUserPlan().increaseSellableDataAmount(originalDataAmount);

            int newDataAmount = request.getSellMobileDataCapacityGb();

            if (newDataAmount > user.getUserPlan().getSellableDataAmount()) {
                throw new GlobalException(TradePostErrorCode.EXCEED_SELL_CAPACITY);
            }

            user.getUserPlan().subtractSellableDataAmount(newDataAmount);
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

        TradePost tradePost = tradePostRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.TRADE_POST_NOT_FOUND));

        tradePost.verifyOwner(tradePost, user);

        if (!tradePost.getTradePostStatus().equals(TradePostStatus.SELLING)) {

            throw new GlobalException(TradePostErrorCode.CANNOT_DELETE_NOT_SELLING_POST);
        }

        int dataToRestore = tradePost.getSellMobileDataCapacityGb();
        user.getUserPlan().increaseSellableDataAmount(dataToRestore);

        tradePost.softDeleteAndStatusDelete();

        return new TradePostCommonRes(tradePost.getId());
    }

    /**
     * 1. 일괄 구매 조회 로직
     */
    @Transactional(readOnly = true)
    public TradePostBulkPurchaseRes readLumSumPurchase(TradePostBulkPurchaseReq request,
        Long userId) {

        User user = getUser(userId);

        List<TradePost> candidates = tradePostRepository.findCheapestCandidates(request,
            user.getUserPlan().getPlan().getCarrier(),
            user.getUserPlan().getPlan().getMobileDataType(), userId);

        List<TradePost> recommendationList = new ArrayList<>();

        int cumulativeGb = 0;
        int cumulativePrice = 0;
        final int desiredGb = request.getDesiredGb();
        final int maxPrice = request.getMaxPrice();

        for (TradePost post : candidates) {

            if ((cumulativePrice + post.getTotalZet() <= maxPrice) &&
                (cumulativeGb + post.getSellMobileDataCapacityGb() <= desiredGb)) {

                recommendationList.add(post);
                cumulativePrice += post.getTotalZet();
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

}
