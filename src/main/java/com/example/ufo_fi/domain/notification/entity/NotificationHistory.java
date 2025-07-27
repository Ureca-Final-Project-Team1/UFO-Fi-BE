package com.example.ufo_fi.domain.notification.entity;

import com.example.ufo_fi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_histories")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType type;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "url")
    private String url;

    @CreatedDate
    @Column(name = "notified_at")
    private LocalDateTime notifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static NotificationHistory of(User user, String title, String body, NotificationType type, String url) {
        return NotificationHistory.builder()
                .type(type)
                .title(title)
                .content(body)
                .url(url)
                .user(user)
                .build();
    }
}
