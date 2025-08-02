package com.example.ufo_fi.v2.payment.presentation.dto.request;

import com.example.ufo_fi.v2.user.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SlackRecoverUserReq {
    private Long id;
    private Role role;
    private String name;
}
