package com.example.ufo_fi.domain.report.dto.response;

import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RollBackReportsReadRes {

    private List<RollBackReportReadRes> rollBackReportsReadRes;

    public static RollBackReportsReadRes from(final List<TradePost> tradePosts) {
        return RollBackReportsReadRes.builder()
                .rollBackReportsReadRes(tradePosts.stream()
                        .map(RollBackReportReadRes::from).toList())
                .build();
    }
}
