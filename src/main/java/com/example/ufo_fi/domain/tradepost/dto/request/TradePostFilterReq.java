package com.example.ufo_fi.domain.tradepost.dto.request;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TradePostFilterReq {

    private Carrier carrier;
    private Integer maxTotalPrice;
    private Integer minTotalPrice;
    private Integer maxCapacity;
    private Integer minCapacity;

    private Integer size = 10;
    private LocalDateTime cursorCreatedAt;
    private Long cursorId;
}
