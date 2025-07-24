package com.example.ufo_fi.domain.report.repository;

import com.example.ufo_fi.domain.report.entity.Report;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import com.example.ufo_fi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByTradePost(TradePost tradePost);

    int countByTradePost(TradePost tradePostProxy);

    int countByReportedUser(User userProxy);
}
