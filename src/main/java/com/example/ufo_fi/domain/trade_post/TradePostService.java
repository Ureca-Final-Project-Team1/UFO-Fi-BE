package com.example.ufo_fi.domain.trade_post;

import com.example.ufo_fi.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradePostService {

    private final TradePostRepository tradePostRepository;


    @Transactional
    public TradePostCommonResponse save(TradePostCreateRequest tradePostCreateRequest, User user) {

        TradePost tradePost = TradePost.from(tradePostCreateRequest, user);
        TradePost savedTradePost = tradePostRepository.save(tradePost);

        return new TradePostCommonResponse(savedTradePost.getId());
    }


}
