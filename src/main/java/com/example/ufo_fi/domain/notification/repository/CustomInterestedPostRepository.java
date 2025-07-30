package com.example.ufo_fi.domain.notification.repository;

import java.util.List;

public interface CustomInterestedPostRepository {
    List<Long> findMatchedUserIdsWithNotificationEnabled(int price, int capacity, int carrierBit, long sellerId);
}
