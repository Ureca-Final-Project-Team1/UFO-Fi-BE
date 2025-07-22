package com.example.ufo_fi.domain.report.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportCreateReq {

    @Schema(description = "신고 내용입니다.")
    private String content;

    @Schema(description = "신고 게시물 id 입니다.")
    private Long postId;

    @Schema(description = "신고당한 사람 id입니다. 현 게시물의 주인")
    private Long reportedUserId;
}
