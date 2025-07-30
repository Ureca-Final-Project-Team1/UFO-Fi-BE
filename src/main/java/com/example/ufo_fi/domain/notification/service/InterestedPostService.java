package com.example.ufo_fi.domain.notification.service;

import com.example.ufo_fi.domain.notification.dto.request.InterestedPostUpdateReq;
import com.example.ufo_fi.domain.notification.entity.InterestedPost;
import com.example.ufo_fi.domain.notification.exception.NotificationErrorCode;
import com.example.ufo_fi.domain.notification.repository.InterestedPostRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterestedPostService {

    private final InterestedPostRepository interestedPostRepository;

    @Transactional
    public void updateInterestedPost(Long userId, InterestedPostUpdateReq request) {

        InterestedPost interestedPost = interestedPostRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(NotificationErrorCode.NO_INTERESTED_POST));

        int carrierBitmask = InterestedCarriers.encode(request.getCarriers());

        interestedPost.update(request, carrierBitmask);
    }
}
