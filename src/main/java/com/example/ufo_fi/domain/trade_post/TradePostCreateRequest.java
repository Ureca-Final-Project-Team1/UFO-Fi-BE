package com.example.ufo_fi.domain.trade_post;


import com.example.ufo_fi.domain.user.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TradePostCreateRequest(
    @NotBlank(message = "제목은 필수입니다.")
    @Size(min = 1, max = 15, message = "제목은 1~15자 이내여야 합니다.")
    String title,

    @Min(value = 1, message = "가격은 1원 이상이여야 됩니다.")
    int price,

    @Min(value = 1, message = "용량은 1GB 이상이어야 됩니다.")
    int capacity,

    @NotNull
    CarrierType carrierType,

    @NotNull
    MobileDataType mobileDataType

) {

    public TradePost toTradePost(User user) {
        return TradePost.of(user, carrierType, mobileDataType, capacity, title, price);
    }
}
