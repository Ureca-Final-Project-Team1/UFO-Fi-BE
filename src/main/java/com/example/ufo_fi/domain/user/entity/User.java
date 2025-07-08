package com.example.ufo_fi.domain.user.entity;

import com.example.ufo_fi.domain.userplan.entity.UserPlan;
import com.example.ufo_fi.global.security.oauth.provider.OAuth2Response;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kakao_id", nullable = false)
    private String kakaoId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_plan_id")
    private UserPlan userPlan;

    /*
     * 첫 회원가입 시 사용하는 정적 팩터리 메서드
     * role은 ROLE_NO_INFO 이다.
     */
    public static User of(OAuth2Response oAuth2Response, Role noInfo){
        return User.builder()
                .email(oAuth2Response.getEmail())
                .role(noInfo)
                .build();
    }
}
