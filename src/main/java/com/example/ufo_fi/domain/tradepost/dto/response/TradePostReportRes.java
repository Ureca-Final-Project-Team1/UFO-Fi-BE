package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.report.entity.Report;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradePostReportRes {
    private String tradePostTitle;
    private String reportContent;
    private Integer reportCount;

    public static TradePostReportRes of(final Report report, final TradePost tradePost, int reportCount) {
        return TradePostReportRes.builder()
                .tradePostTitle(tradePost.getTitle())
                .reportContent(report.getContent())
                .reportCount(reportCount)
                .build();
    }
}
