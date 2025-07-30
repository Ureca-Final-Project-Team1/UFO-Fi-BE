package com.example.ufo_fi.domain.notification.dto.response;

import com.example.ufo_fi.domain.notification.entity.NotificationHistory;
import com.example.ufo_fi.domain.notification.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRes {
    private NotificationType type;
    private String title;
    private String content;
    private String url;
    private LocalDateTime notifiedAt;

    public static NotificationRes from(NotificationHistory history) {
        return NotificationRes.builder()
                .type(history.getType())
                .title(history.getTitle())
                .content(history.getContent())
                .url(history.getUrl())
                .notifiedAt(history.getNotifiedAt())
                .build();
    }
}
