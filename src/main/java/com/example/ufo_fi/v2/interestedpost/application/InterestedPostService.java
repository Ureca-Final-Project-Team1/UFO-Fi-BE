package com.example.ufo_fi.v2.interestedpost.application;

import com.example.ufo_fi.v2.interestedpost.domain.InterestedPost;
import com.example.ufo_fi.v2.interestedpost.domain.InterestedPostManager;
import com.example.ufo_fi.v2.interestedpost.presentation.dto.request.InterestedPostUpdateReq;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterestedPostService {

    private final InterestedPostManager interestedPostManager;
    private final EntityManager entityManager;

    public void updateInterestedPost(Long userId, InterestedPostUpdateReq request) {

        User userProxy = entityManager.getReference(User.class, userId);
        InterestedPost interestedPost = interestedPostManager.findByUser(userProxy);

        int carrierBit = interestedPostManager.encodeCarriers(request.getCarriers());

        // TODO dto는 어디까지 전달되도 되는가
        interestedPostManager.updateInterestedPost(interestedPost, request, carrierBit);
    }
}
