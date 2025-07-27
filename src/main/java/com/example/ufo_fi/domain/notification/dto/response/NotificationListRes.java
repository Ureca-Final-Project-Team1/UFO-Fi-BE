package com.example.ufo_fi.domain.notification.dto.response;

import com.example.ufo_fi.domain.notification.entity.NotificationHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationListRes {
    List<NotificationRes> notifications;

    public static NotificationListRes from(List<NotificationHistory> histories) {
        List<NotificationRes> list = histories.stream()
                .map(NotificationRes::from)
                .toList();

        return NotificationListRes.builder()
                .notifications(list)
                .build();
    }
}
