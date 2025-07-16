package com.example.ufo_fi.domain.follow.dto.response;

import com.example.ufo_fi.domain.follow.entity.Follow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowingCreateRes {
    private Long id;

    public static FollowingCreateRes from(final Follow follow){
        return FollowingCreateRes.builder()
                .id(follow.getId())
                .build();
    }
}
