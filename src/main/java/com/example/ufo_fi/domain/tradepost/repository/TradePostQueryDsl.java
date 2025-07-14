package com.example.ufo_fi.domain.tradepost.repository;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostQueryReq;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface TradePostQueryDsl {

    Slice<TradePost> findPostsByConditions(TradePostQueryReq condition, Pageable pageable);

    List<TradePost> findCheapestCandidates(TradePostBulkPurchaseReq condition,
        Carrier carrier, MobileDataType mobileDataType, Long userId);
}

