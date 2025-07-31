package com.example.ufo_fi.v2.notification.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TradeCompletedEvent {
    private Long sellerId;
}
