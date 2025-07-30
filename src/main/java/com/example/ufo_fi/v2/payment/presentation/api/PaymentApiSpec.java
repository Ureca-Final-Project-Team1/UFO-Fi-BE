package com.example.ufo_fi.v2.payment.presentation.api;

import com.example.ufo_fi.v2.payment.presentation.dto.request.ConfirmReq;
import com.example.ufo_fi.v2.payment.presentation.dto.request.PaymentReq;
import com.example.ufo_fi.v2.payment.presentation.dto.response.ConfirmRes;
import com.example.ufo_fi.v2.payment.presentation.dto.response.PaymentRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Charge API", description = "충전하기 API")
public interface PaymentApiSpec {

    @Operation(summary = "충전하기 API", description = "ZET 충전을 시도합니다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/v1/mypage/zet-charges")
    ResponseEntity<ResponseBody<PaymentRes>> chargeZet(
            @RequestBody PaymentReq request,
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );


    @Operation(summary = "결제 정보 검증 및 승인 API", description = "결제 정보를 검증하고 최종 결제 승인을 요청합니다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/v1/payment/confirm")
    ResponseEntity<ResponseBody<ConfirmRes>> confirm(
            @RequestBody ConfirmReq request,
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );
}
