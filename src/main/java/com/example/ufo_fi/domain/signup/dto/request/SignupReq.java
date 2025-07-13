package com.example.ufo_fi.domain.signup.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupReq {
    private UserInfoReq userInfoReq;    //유저 기본 정보
    private UserPlanReq userPlanReq;    //요금제 정보
}
