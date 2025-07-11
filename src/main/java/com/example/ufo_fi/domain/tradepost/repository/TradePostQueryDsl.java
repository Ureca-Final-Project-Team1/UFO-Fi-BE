package com.example.ufo_fi.domain.tradepost.repository;

import com.example.ufo_fi.domain.tradepost.dto.request.TradePostFilterReq;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface TradePostQueryDsl {

    Slice<TradePost> findByCursorPaging(LocalDateTime cursor, Long lastId, Pageable pageable);
    Slice<TradePost> searchWithPagination(TradePostFilterReq request);
}
