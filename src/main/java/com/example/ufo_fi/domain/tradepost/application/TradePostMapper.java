package com.example.ufo_fi.domain.tradepost.application;

import com.example.ufo_fi.domain.tradepost.domain.TradePost;
import com.example.ufo_fi.domain.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.domain.tradepost.presentation.dto.request.TradePostCreateReq;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.entity.UserPlan;
import org.springframework.stereotype.Component;

@Component
public class TradePostMapper {

    public TradePost toTradePost(TradePostCreateReq request, TradePostStatus tradePostStatus,
        User user, UserPlan userPlan) {

        TradePost tradePost = TradePost.builder()
            .user(user)
            .title(request.getTitle())
            .zetPerUnit(request.getZetPerUnit())
            .sellMobileDataCapacityGb(request.getSellDataAmount())
            .carrier(userPlan.getPlan().getCarrier())
            .mobileDataType(userPlan.getPlan().getMobileDataType())
            .tradePostStatus(tradePostStatus)
            .build();

        tradePost.saveTotalPrice();

        return tradePost;
    }


}
