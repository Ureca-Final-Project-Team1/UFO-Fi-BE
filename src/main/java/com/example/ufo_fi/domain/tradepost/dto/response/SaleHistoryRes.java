package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import com.example.ufo_fi.domain.tradepost.entity.TradeHistory;
import com.example.ufo_fi.domain.tradepost.entity.TradePostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class SaleHistoryRes {

    @Schema(description = "게시글 식별 번호")
    private Long postId;

    @Schema(example = "SELLING : 판매 중\n"
            + "SOLD_OUT : 판매 완료\n"
            + "REPORTED : 신고 완료\n"
            + "EXPIRED : 판매 종료\n"
            + "DELETED ", description = "판매 게시글 상태")
    private TradePostStatus status;

    @Schema(description = "판매 게시글 생성일")
    private LocalDateTime createdAt;

    @Schema(example = "SKT, KT, LGU", description = "통신사")
    private Carrier carrier;

    @Schema(description = "판매 게시글 제목")
    private String title;

    @Schema(description = "판매 게시글 총 ZET")
    private Integer totalZet;

    @Schema(example = "LTE, _5G", description = "모바일 데이터 타입")
    private MobileDataType mobileDataType;
}
