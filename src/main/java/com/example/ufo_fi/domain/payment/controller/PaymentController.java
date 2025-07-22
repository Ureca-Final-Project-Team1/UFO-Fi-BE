package com.example.ufo_fi.domain.payment.controller;

import com.example.ufo_fi.domain.payment.controller.api.PaymentApiSpec;
import com.example.ufo_fi.domain.payment.dto.request.ConfirmReq;
import com.example.ufo_fi.domain.payment.dto.request.PaymentReq;
import com.example.ufo_fi.domain.payment.dto.response.ConfirmRes;
import com.example.ufo_fi.domain.payment.dto.response.PaymentRes;
import com.example.ufo_fi.domain.payment.service.PaymentService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentApiSpec {

    private final PaymentService chargeService;

    /**
     * 충전하기 요청
     * 결제 임시 정보 반환
     */
    @Override
    public ResponseEntity<ResponseBody<PaymentRes>> chargeZet(
            PaymentReq request,
            DefaultUserPrincipal defaultUserPrincipal) {

        return ResponseEntity
                .ok(ResponseBody
                        .success(chargeService.charge(defaultUserPrincipal.getId(), request)));
    }

    /**
     * 검증 및 승인 요청
     */
    @Override
    public ResponseEntity<ResponseBody<ConfirmRes>> confirm(
            ConfirmReq request,
            DefaultUserPrincipal defaultUserPrincipal) {

        return ResponseEntity
                .ok(ResponseBody
                        .success(chargeService.confirm(request)));
    }
}
