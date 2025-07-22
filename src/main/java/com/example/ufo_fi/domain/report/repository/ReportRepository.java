package com.example.ufo_fi.domain.report.repository;

import com.example.ufo_fi.domain.report.entity.Report;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    int countByPostId(Long postId);

    int countByUserId(Long userId);

    List<Report> findByTradePost(TradePost tradePost);
}
