package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import com.example.ufo_fi.domain.tradepost.entity.TradeHistory;
import com.example.ufo_fi.domain.tradepost.entity.TradePostStatus;
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
    private Long postId;
    private TradePostStatus status;
    private LocalDateTime createdAt;
    private Carrier carrier;
    private String title;
    private Integer totalZet;

    private MobileDataType mobileDataType;
}
