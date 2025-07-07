package com.example.ufo_fi.domain.trade_post;

import com.example.ufo_fi.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradePostService {

    private final TradePostRepository tradePostRepository;


    /**
     * 1. 유저 상세 정보가 없으면 exception을 터트린다.
     * 2. id 반환
     */
    @Transactional
    public TradePostCommonResponse save(TradePostCreateRequest tradePostCreateRequest, User user) {

        TradePost tradePost = tradePostCreateRequest.toTradePost(user);
        TradePost savedTradePost = tradePostRepository.save(tradePost);

        return new TradePostCommonResponse(savedTradePost.getId());
    }


}
