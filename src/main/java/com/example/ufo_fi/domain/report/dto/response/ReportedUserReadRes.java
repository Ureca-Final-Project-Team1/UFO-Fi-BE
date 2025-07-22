package com.example.ufo_fi.domain.report.dto.response;

import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import com.example.ufo_fi.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportedUserReadRes {

    @Schema(description = "신고된 유저 아이디입니다.")
    private Long userid;

    @Schema(description = "신고된 유저 닉네임입니다.")
    private String nickname;

    @Schema(description = "신고된 유저의 신고 게시물들입니다.")
    private List<ReportedUserTradePost> reportedUserTradePosts;

    public static ReportedUserReadRes from(final User user, final List<TradePost> tradePosts) {
        return ReportedUserReadRes.builder()
                .userid(user.getId())
                .nickname(user.getNickname())
                .reportedUserTradePosts(tradePosts.stream()
                        .map(ReportedUserTradePost::from).toList())
                .build();
    }
}
