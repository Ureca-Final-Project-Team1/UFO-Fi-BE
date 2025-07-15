package com.example.ufo_fi.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoReq {
    private String name;
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "올바른 휴대폰 번호 형식이 아닙니다.")
    private String phoneNumber;
}
