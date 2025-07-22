package com.example.ufo_fi.domain.report.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RollBackReportReadRes {

    @Schema(description = "신고 게시물 아이디")
    private Long tradePostId;

    @Schema(description = "신고당한 유저 아이디")
    private Long userId;

    @Schema(description = "신고당한 유저 닉넴")
    private String nickname;

    @Schema(description = "신고당한 유저 실명")
    private String name;

    @Schema(description = "신고 내용")
    private String content;

    public static RollBackReportReadRes from(){

    }
}
