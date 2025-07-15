package com.example.ufo_fi.domain.tradepost.dto.request;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradePostQueryReq {

    private Carrier carrier;
    private Integer maxTotalZet;
    private Integer minTotalZet;
    private Integer maxCapacity;
    private Integer minCapacity;
    private String reputation;

    private Long cursorId;

    private Integer size;

}
