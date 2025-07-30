package com.example.ufo_fi.domain.notification.event;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatedPostEvent {
    private Long sellerId;
    private Long postId;
    private Carrier carrier;
    private int zet;
    private int capacity;
}
