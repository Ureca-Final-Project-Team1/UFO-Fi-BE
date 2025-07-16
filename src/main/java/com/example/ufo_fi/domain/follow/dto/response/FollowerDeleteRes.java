package com.example.ufo_fi.domain.follow.dto.response;

import com.example.ufo_fi.domain.follow.entity.Follow;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowerDeleteRes {
    private Long id;

    public static FollowerDeleteRes from(final Follow follow){
        return FollowerDeleteRes.builder()
                .id(follow.getId())
                .build();
    }
}
