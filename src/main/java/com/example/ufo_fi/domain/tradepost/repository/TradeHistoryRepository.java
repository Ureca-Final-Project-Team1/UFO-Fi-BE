package com.example.ufo_fi.domain.tradepost.repository;

import com.example.ufo_fi.domain.tradepost.entity.TradeHistory;
import com.example.ufo_fi.domain.tradepost.entity.TradeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {

    @Query("""
    SELECT th
    FROM TradeHistory th
    JOIN FETCH th.tradePost tp
    JOIN FETCH th.user u
    LEFT JOIN FETCH th.report r
    WHERE th.tradeType = :tradeType 
    AND th.user.id = :userId
    """)
    List<TradeHistory> findByUserIdAndStatus(
            @Param(value = "tradeType") TradeType tradeType,
            @Param(value = "userId") Long userId
    );
}
