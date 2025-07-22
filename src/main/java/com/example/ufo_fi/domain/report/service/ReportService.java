package com.example.ufo_fi.domain.report.service;

import com.example.ufo_fi.domain.notification.event.AccountSuspendEvent;
import com.example.ufo_fi.domain.report.dto.request.ReportCreateReq;
import com.example.ufo_fi.domain.report.entity.Report;
import com.example.ufo_fi.domain.report.exception.ReportErrorCode;
import com.example.ufo_fi.domain.report.repository.ReportRepository;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import com.example.ufo_fi.domain.tradepost.repository.TradePostRepository;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import com.google.firebase.database.core.Repo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final TradePostRepository tradePostRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 1. 신고 생성
     * 2. 해당 게시물 신고 갯수 조회
     * 3. 3회 이상인가?
     * => 3-1. Yes 해당 게시물 상태 업데이트
     * => 3-2. No 그냥 진행
     * 4. 해당 게시물의 유저는 몇 회 신고 누적이 있는가?
     * => 4-1. 유저 정지
     * => 4-2. 그냥 진행
     */
    @Transactional
    public void reportTradePost(Long userId, ReportCreateReq reportCreateReq){
        User user = userRepository.getReferenceById(userId);
        User reportedUser = userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(ReportErrorCode.NO_REPORTED_USER));
        TradePost tradePost = tradePostRepository.getReferenceById(reportCreateReq.getPostId());
        Report report = Report.of(user, reportedUser, tradePost, reportCreateReq);

        reportRepository.save(report);

        int tradePostReportCount = reportRepository.countByPostId(reportCreateReq.getPostId());
        if(tradePostReportCount >= 3){
            tradePost.updateStatusReported();
        }

        int userReportCount = reportRepository.countByUserId(userId);
        if(userReportCount >= 3){
            reportedUser.updateStatusReported();
            applicationEventPublisher.publishEvent(new AccountSuspendEvent(reportedUser.getId()));
        }
    }


}
