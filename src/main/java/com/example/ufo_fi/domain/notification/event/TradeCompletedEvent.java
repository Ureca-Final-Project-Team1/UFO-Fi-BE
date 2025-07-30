package com.example.ufo_fi.domain.notification.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TradeCompletedEvent {
    private Long sellerId;
}
