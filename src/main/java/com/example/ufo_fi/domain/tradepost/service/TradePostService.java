package com.example.ufo_fi.domain.tradepost.service;

import com.example.ufo_fi.domain.tradepost.dto.request.TradePostCreateReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostSearchReq;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostCommonRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostSearchRes;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import com.example.ufo_fi.domain.tradepost.entity.TradePostStatus;
import com.example.ufo_fi.domain.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.domain.tradepost.repository.TradePostRepository;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.useraccount.repository.UserAccountRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradePostService {

    private final TradePostRepository tradePostRepository;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    /**
     * 1. 게시물 생성
     */
    @Transactional
    public TradePostCommonRes createTradePost(TradePostCreateReq request, Long userId) {
        // 판매자를 찾음
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_NOT_FOUND));

        //1. 판매자가 계정 정보를 등록했는가
        if (!userAccountRepository.existsByUser(user)) {
            throw new GlobalException(TradePostErrorCode.ACCOUNT_NOT_REGISTERED);
        }

        int userAvailableData = user.getUserPlan().getSellMobileDataCapacityGb();
        int requestSellData = request.getSellMobileDataCapacityGb();

        if (requestSellData > userAvailableData) {
            throw new GlobalException(TradePostErrorCode.EXCEED_SELL_CAPACITY);
        }

        TradePost tradePost = TradePost.of(request, false, false, TradePostStatus.SELLING, 0, user);

        //판매 등록한 용량을 빼준다.
        user.getUserPlan().subtractSellableDataAmount(requestSellData);
        TradePost savedTradePost = tradePostRepository.save(tradePost);

        return new TradePostCommonRes(savedTradePost.getId());
    }

    /**
     * 1. 게시물 삭제
     */

    @Transactional
    public TradePostCommonRes deleteTradePost(Long postId, Long userId) {
        // 사용자 조회
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_NOT_FOUND));

        // 판매 게시물 조회
        TradePost tradePost = tradePostRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.TRADE_POST_NOT_FOUND));

        if (!tradePost.getUser().getId().equals(userId)) {
            throw new GlobalException(TradePostErrorCode.NO_AUTHORITY_TO_DELETE);
        }

        if (!tradePost.getTradePostStatus().equals(TradePostStatus.SELLING)) {
            throw new GlobalException(TradePostErrorCode.CANNOT_DELETE_NOT_SELLING_POST);
        }

        // 판매 등록으로 차감되었던 사용자 데이터 용량 즉시 복원
        int dataToRestore = tradePost.getSellMobileDataCapacityGb();
        user.getUserPlan().increaseSellableDataAmount(dataToRestore);

        // 게시물 상태를 삭제로 변경
        tradePost.softDelete();
        tradePost.statusDelete();

        return new TradePostCommonRes(tradePost.getId());
    }

    /**
     * 1. 게시물 조회
     */
    public TradePostSearchRes readTradePostList(Long userId, TradePostSearchReq request) {

        Pageable pageable = PageRequest.of(0, request.getSize());
        LocalDateTime nextCursor;

        List<TradePost> posts = tradePostRepository.findByCursorPaging(
            request.getCursorOrDefault(), pageable
        );

        if (posts.isEmpty()) { //다음 커서는 존재하지 않늗다.
            nextCursor = null;
        } else {//한 개라도 존재시 -> 기준점(cursor)를 기반으로 조회
            nextCursor = posts.get(posts.size() - 1).getCreatedAt();
        }

        return TradePostSearchRes.of(posts, nextCursor);
    }
}
