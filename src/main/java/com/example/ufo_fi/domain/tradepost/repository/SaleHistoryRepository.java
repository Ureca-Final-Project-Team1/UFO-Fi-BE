package com.example.ufo_fi.domain.tradepost.repository;


import com.example.ufo_fi.domain.tradepost.entity.SaleHistory;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleHistoryRepository extends JpaRepository<SaleHistory, Long> {

    Long findByTradePost(TradePost tradePost);
}
