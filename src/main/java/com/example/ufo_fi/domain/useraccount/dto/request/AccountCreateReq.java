package com.example.ufo_fi.domain.useraccount.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountCreateReq {
    private String bank;
    private String bankAccount;
    private String password;
}
