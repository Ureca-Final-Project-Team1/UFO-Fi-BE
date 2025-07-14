package com.example.ufo_fi.domain.tradepost.repository;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostFilterReq;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import com.example.ufo_fi.domain.tradepost.entity.TradePostStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface TradePostQueryDsl {

    Slice<TradePost> findRecentPostByCursor(LocalDateTime cursor, Long lastId,
        List<TradePostStatus> statuses, Pageable pageable);

    Slice<TradePost> findRecentPostsByCursor(TradePostFilterReq request);

    List<TradePost> findCheapestCandidates(TradePostBulkPurchaseReq condition,
        Carrier carrier, MobileDataType mobileDataType, Long userId);
}

