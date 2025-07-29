package com.example.ufo_fi.domain.payment.presentation.dto.request;

import com.example.ufo_fi.domain.user.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SlackRecoverUserReq {
    private Long id;
    private Role role;
    private String name;
}
