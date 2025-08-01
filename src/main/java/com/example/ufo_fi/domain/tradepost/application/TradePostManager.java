package com.example.ufo_fi.domain.tradepost.application;

import com.example.ufo_fi.domain.bannedword.filter.BannedWordFilter;
import com.example.ufo_fi.domain.tradepost.domain.TradePost;
import com.example.ufo_fi.domain.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.domain.tradepost.infrastructure.TradePostRepository;
import com.example.ufo_fi.domain.tradepost.presentation.dto.request.TradePostQueryReq;
import com.example.ufo_fi.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TradePostManager {

    private final TradePostRepository tradePostRepository;
    private final BannedWordFilter bannedWordFilter;

    public TradePost validateAndFindByIdWithLock(Long postId) {

        return tradePostRepository.findByIdWithLock(postId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.TRADE_POST_NOT_FOUND));
    }

    public TradePost validateAndFindById(Long postId) {

        return tradePostRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.TRADE_POST_NOT_FOUND));
    }

    public Slice<TradePost> findPostsByCondition(TradePostQueryReq request, Pageable pageable) {

        return tradePostRepository.findPostsByConditions(
            request,
            pageable
        );
    }

    public TradePost saveTradePost(TradePost tradePost) {

        return tradePostRepository.save(tradePost);
    }

    public void validateBannedWord(String content) {
        bannedWordFilter.filter(content);
    }

    public void validatePostsExistence(Slice<TradePost> posts) {

        if (posts.isEmpty()) {

            throw new GlobalException(TradePostErrorCode.NO_TRADE_POST_FOUND);
        }
    }


}
