package com.example.ufo_fi.domain.user.dto.response;

import com.example.ufo_fi.domain.user.entity.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountReadRes {
    private String bank;
    private String bankAccount;

    public static AccountReadRes from(final UserAccount userAccount) {
        return AccountReadRes.builder()
                .bank(userAccount.getBank())
                .bankAccount(userAccount.getBankAccount())
                .build();
    }
}
