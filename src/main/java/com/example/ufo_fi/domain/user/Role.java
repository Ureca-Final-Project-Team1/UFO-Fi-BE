package com.example.ufo_fi.domain.user;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_NO_INFO    //NO_INFO를 가진 사용자일 경우 온보딩으로 redirect 해줘야함
    ;
}
