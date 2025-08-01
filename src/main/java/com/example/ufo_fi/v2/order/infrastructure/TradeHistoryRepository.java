package com.example.ufo_fi.v2.order.infrastructure;

import com.example.ufo_fi.v2.order.domain.TradeHistory;
import com.example.ufo_fi.v2.order.domain.TradeType;
import java.util.List;
import java.util.Optional;
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
    Optional<TradeHistory> findByPurchaseHistoryIdAndStatus(
        @Param(value = "tradeType") TradeType tradeType,
        @Param(value = "purchaseHistoryId") Long purchaseHistoryId
    );
}
