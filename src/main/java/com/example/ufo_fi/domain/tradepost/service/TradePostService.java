package com.example.ufo_fi.domain.tradepost.service;

import com.example.ufo_fi.domain.tradepost.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostCreateReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostFilterReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostSearchReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostUpdateReq;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostBulkPurchaseRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostCommonRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostFilterRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostSearchRes;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import com.example.ufo_fi.domain.tradepost.entity.TradePostStatus;
import com.example.ufo_fi.domain.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.domain.tradepost.repository.TradePostRepository;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.domain.useraccount.repository.UserAccountRepository;
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

    private final TradePostRepository tradePostRepository;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    /**
     * 게시물 생성
     */
    @Transactional
    public TradePostCommonRes createTradePost(TradePostCreateReq request, Long userId) {

        User user = getUser(userId);

        if (!userAccountRepository.existsByUser(user)) {

            throw new GlobalException(TradePostErrorCode.ACCOUNT_NOT_REGISTERED);
        }

        int userAvailableData = user.getUserPlan().getSellMobileDataCapacityGb();
        int requestSellData = request.getSellableDataAmount();

        if (requestSellData > userAvailableData) {

            throw new GlobalException(TradePostErrorCode.EXCEED_SELL_CAPACITY);
        }

        TradePost tradePost = TradePost.of(request, false, false, TradePostStatus.SELLING, 0, user);

        user.getUserPlan().subtractSellableDataAmount(requestSellData);
        tradePost.saveTotalPrice();// total 가격 저장

        TradePost savedTradePost = tradePostRepository.save(tradePost);

        return new TradePostCommonRes(savedTradePost.getId());
    }


    /**
     * 게시물 조회(cursor 기반 최신순 정렬)
     */
    public TradePostSearchRes readTradePostList(TradePostSearchReq request, Long userId) {

        Pageable pageable = PageRequest.of(0, request.getSize());

        List<TradePostStatus> statuses = List.of(TradePostStatus.SELLING, TradePostStatus.SOLD_OUT);

        Slice<TradePost> posts = tradePostRepository.findRecentPostByCursor(
            request.getCursor(), request.getLastId(), statuses, pageable
        );

        validatePostsExistence(posts);

        return TradePostSearchRes.of(posts);
    }

    /**
     * 게시물 필터링(cursor 기반 유저가 원하는대로 조회)
     */
    @Transactional
    public TradePostFilterRes readFilterList(TradePostFilterReq request, Long userId) {

        Slice<TradePost> posts = tradePostRepository.findRecentPostsByCursor(request);

        validatePostsExistence(posts);

        return TradePostFilterRes.from(posts);
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
            user.getUserPlan().increaseSellableDataAmount(tradePost.getSellMobileDataCapacityGb());
            user.getUserPlan().subtractSellableDataAmount(request.getSellMobileDataCapacityGb());
        }

        //비교 로직 추가 -> 구매하기

        tradePost.saveTotalPrice();// total 가격 저장
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
    public TradePostBulkPurchaseRes readRecommendation(TradePostBulkPurchaseReq request,
        Long userId) {

        User user = getUser(userId);

        List<TradePost> candidates = tradePostRepository.findCheapestCandidates(request,
            user.getUserPlan().getCarrier(), user.getUserPlan().getMobileDataType(), userId);

        List<TradePost> recommendationList = new ArrayList<>();

        int cumulativeGb = 0;
        int cumulativePrice = 0;
        final int desiredGb = request.getDesiredGb();
        final int maxPrice = request.getMaxPrice();

        for (TradePost post : candidates) {

            if ((cumulativePrice + post.getTotalPrice() <= maxPrice) &&
                (cumulativeGb + post.getSellMobileDataCapacityGb() <= desiredGb)) {

                recommendationList.add(post);
                cumulativePrice += post.getTotalPrice();
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
