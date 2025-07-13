package com.example.ufo_fi.domain.mypage.controller.api;

import com.example.ufo_fi.domain.user.dto.request.AccountCreateReq;
import com.example.ufo_fi.domain.user.dto.response.AccountCreateRes;
import com.example.ufo_fi.domain.user.dto.response.AccountReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Mypage API", description = "마이페이지 계좌 API")
public interface MyPageAccountApiSpec {

    @Operation(summary = "마이페이지 계좌 조회 API", description = "내 계좌 정보를 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/mypage/account")
    ResponseEntity<ResponseBody<AccountReadRes>> readAccount(@RequestParam Long userId);

    @Operation(summary = "마이페이지 계좌 및 간편 비밀번호 등록 API", description = "내 계좌를 등록한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/v1/mypage/account")
    ResponseEntity<ResponseBody<AccountCreateRes>> createAccount(
            @RequestParam Long userId,
            @RequestBody AccountCreateReq accountCreateReq
    );
}
