package com.example.ufo_fi.domain.follow.dto.response;

import com.example.ufo_fi.v2.follow.domain.Follow;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowerDeleteRes {

    @Schema(description = "내가 삭제한 팔로우 식별번호")
    private Long id;

    public static FollowerDeleteRes from(final Follow follow){
        return FollowerDeleteRes.builder()
                .id(follow.getId())
                .build();
    }
}
