package com.example.ufo_fi.v2.interestedpost.application;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.interestedpost.domain.InterestedPost;
import com.example.ufo_fi.v2.interestedpost.persistence.InterestedPostRepository;
import com.example.ufo_fi.v2.interestedpost.presentation.dto.request.InterestedPostUpdateReq;
import com.example.ufo_fi.v2.notification.exception.NotificationErrorCode;
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
