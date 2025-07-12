package com.example.ufo_fi.domain.notification.event;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import lombok.Getter;

@Getter
public class PostEvent {
    private Long sellerId;
    private Carrier carrier;
    private int price;
    private int capacity;
}
