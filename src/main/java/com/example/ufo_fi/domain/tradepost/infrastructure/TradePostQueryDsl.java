package com.example.ufo_fi.domain.tradepost.infrastructure;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import com.example.ufo_fi.domain.tradepost.domain.TradePost;
import com.example.ufo_fi.domain.tradepost.presentation.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.domain.tradepost.presentation.dto.request.TradePostQueryReq;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface TradePostQueryDsl {

    Slice<TradePost> findPostsByConditions(TradePostQueryReq condition, Pageable pageable);

    List<TradePost> findCheapestCandidates(TradePostBulkPurchaseReq condition,
        Carrier carrier, MobileDataType mobileDataType, Long userId);
}

