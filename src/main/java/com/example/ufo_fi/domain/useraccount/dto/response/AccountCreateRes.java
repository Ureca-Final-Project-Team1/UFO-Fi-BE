package com.example.ufo_fi.domain.useraccount.dto.response;

import com.example.ufo_fi.domain.useraccount.entity.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRes {
    private Long id;

    public static AccountCreateRes from(final UserAccount userAccount){
        return AccountCreateRes.builder()
                .id(userAccount.getId())
                .build();
    }
}
