package com.example.ufo_fi.domain.notification.dto.request;

import com.example.ufo_fi.domain.notification.common.InterestedCarriers;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InterestedPostUpdateReq {

    @Schema(description = "관심 있는 통신사")
    private List<InterestedCarriers> carriers;

    @Schema(description = "최대 용량")
    private Integer interestedMaxCapacity;

    @Schema(description = "최소 용량")
    private Integer interestedMinCapacity;

    @Schema(description = "최대 가격")
    private Integer interestedMaxPrice;

    @Schema(description = "최소 가격")
    private Integer interestedMinPrice;
}
