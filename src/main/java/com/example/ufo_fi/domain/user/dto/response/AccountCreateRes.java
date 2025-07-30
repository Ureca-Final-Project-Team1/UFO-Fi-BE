package com.example.ufo_fi.domain.user.dto.response;

import com.example.ufo_fi.v2.user.domain.UserAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRes {

    @Schema(description = "내 고유 식별 번호")
    private Long id;

    public static AccountCreateRes from(final UserAccount userAccount){
        return AccountCreateRes.builder()
                .id(userAccount.getId())
                .build();
    }
}
