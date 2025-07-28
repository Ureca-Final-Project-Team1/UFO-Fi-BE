package com.example.ufo_fi.domain.payment.domain;

import com.example.ufo_fi.domain.payment.infrastructure.toss.response.ConfirmResult;
import com.example.ufo_fi.domain.payment.presentation.dto.request.ConfirmReq;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StateMetaData {
    private ConfirmReq confirmReq;
    private ConfirmResult confirmResult;
}
