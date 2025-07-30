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
public class AccountReadRes {

    @Schema(description = "내 은행")
    private String bank;

    @Schema(description = "내 계좌")
    private String bankAccount;

    public static AccountReadRes from(final UserAccount userAccount) {
        return AccountReadRes.builder()
                .bank(userAccount.getBank())
                .bankAccount(userAccount.getBankAccount())
                .build();
    }
}
