package com.example.ufo_fi.domain.report.dto.response;

import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportedUserTradePost {

    @Schema(description = "신고된 게시물 아이디입니다.")
    private Long postId;

    @Schema(description = "해당 게시물 이름입니다.")
    private String postTitle;

    public static ReportedUserTradePost from(final TradePost tradePost) {
        return ReportedUserTradePost.builder()
            .postId(tradePost.getId())
            .postTitle(tradePost.getTitle())
            .build();
    }
}
