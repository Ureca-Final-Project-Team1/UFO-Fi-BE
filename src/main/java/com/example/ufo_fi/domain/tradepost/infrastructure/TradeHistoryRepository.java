package com.example.ufo_fi.domain.tradepost.infrastructure;

import com.example.ufo_fi.domain.tradepost.domain.TradeHistory;
import com.example.ufo_fi.domain.tradepost.domain.TradeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {

    @Query("""
        SELECT DISTINCT th
        FROM TradeHistory th
        JOIN FETCH th.tradePost tp
        JOIN FETCH th.user u
        LEFT JOIN FETCH tp.reports r
        WHERE th.tradeType = :tradeType
        AND th.user.id = :userId
        """)
    List<TradeHistory> findByUserIdAndStatus(
        @Param(value = "tradeType") TradeType tradeType,
        @Param(value = "userId") Long userId
    );

    @Query("""
        SELECT DISTINCT th
        FROM TradeHistory th
        JOIN FETCH th.tradePost tp
        JOIN FETCH th.user u
        LEFT JOIN FETCH tp.reports r
        WHERE th.tradeType = :tradeType
        AND th.id = :purchaseHistoryId
        """)
    TradeHistory findByPurchaseHistoryIdAndStatus(
        @Param(value = "tradeType") TradeType tradeType,
        @Param(value = "purchaseHistoryId") Long purchaseHistoryId
    );
}
