package com.example.ufo_fi.domain.notification.processor;

import com.example.ufo_fi.domain.notification.event.CreatedPostEvent;
import com.example.ufo_fi.domain.notification.event.NotificationTemplate;
import com.example.ufo_fi.domain.notification.repository.InterestedPostRepository;
import com.example.ufo_fi.domain.notification.service.FcmService;
import com.example.ufo_fi.domain.notification.service.InterestedCarriers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 새 상품 등록 알림 프로세서
 */
@Component
@RequiredArgsConstructor
public class CreatedPostNotificationProcessor {
    private final InterestedPostRepository interestedPostRepository;
    private final FcmService fcmService;

    public void process(CreatedPostEvent event) {
        Long sellerId = event.getSellerId();

        int carrier = InterestedCarriers.fromCarrier(event.getCarrier()).getBit();
        int zet = event.getZet();
        int capacity = event.getCapacity();

        // TODO: 팔로워 조회(2차)
        // List<Long> followerIds = followingRepository.findFollowerIdsBySellerId(sellerId);

        // 2. 관심 상품 사용자 및 알림 활성 사용자 조회
        // TODO: 관심상품에 데이터 타입 없음
        List<Long> enabledUserIds = interestedPostRepository.findMatchedUserIdsWithNotificationEnabled(zet, capacity, carrier);

        // 3. 메시지 조립 (템플릿 기반)
        NotificationTemplate template = NotificationTemplate.INTERESTED_PRODUCT;
        String title = template.getTitle();
        String body = template.getBody();

        // 4. 전송
        fcmService.sendMulticastByUserIds(enabledUserIds, title, body);
    }
}
