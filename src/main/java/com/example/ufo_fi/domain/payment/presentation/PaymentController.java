package com.example.ufo_fi.domain.payment.presentation;

import com.example.ufo_fi.domain.payment.presentation.api.PaymentApiSpec;
import com.example.ufo_fi.domain.payment.presentation.dto.request.ConfirmReq;
import com.example.ufo_fi.domain.payment.presentation.dto.request.PaymentReq;
import com.example.ufo_fi.domain.payment.presentation.dto.request.SlackRecoverReq;
import com.example.ufo_fi.domain.payment.presentation.dto.response.ConfirmRes;
import com.example.ufo_fi.domain.payment.presentation.dto.response.PaymentRes;
import com.example.ufo_fi.domain.payment.application.PaymentService;
import com.example.ufo_fi.domain.payment.presentation.dto.response.SlackRecoverRes;
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
            PaymentReq paymentReq,
            DefaultUserPrincipal defaultUserPrincipal) {

        return ResponseEntity
                .ok(ResponseBody
                        .success(chargeService.charge(defaultUserPrincipal.getId(), paymentReq)));
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

    /**
     * ToDo: 복구로직 manager들 만들어지면 추가
     */
    @Override
    public ResponseEntity<ResponseBody<SlackRecoverRes>> recover(
            SlackRecoverReq slackRecoverReq
    ) {
        return ResponseEntity
                .ok(ResponseBody.success(
                        chargeService.recover(slackRecoverReq)));
    }
}
