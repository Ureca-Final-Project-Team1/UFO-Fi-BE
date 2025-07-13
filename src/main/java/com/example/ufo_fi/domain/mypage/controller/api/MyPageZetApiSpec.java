package com.example.ufo_fi.domain.mypage.controller.api;

import com.example.ufo_fi.domain.user.dto.request.ZetPurchaseReq;
import com.example.ufo_fi.domain.user.dto.response.ZetPurchaseRes;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Mypage API", description = "마이페이지 Zet 구매 API")
public interface MyPageZetApiSpec {

    @Operation(summary = "ZET 구매 API", description = "ZET 코인을 구매한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/mypage/zet")
    ResponseEntity<ResponseBody<ZetPurchaseRes>> purchaseZet(
            @RequestParam Long userId,
            @RequestBody @Valid ZetPurchaseReq zetPurchaseReq
    );
}
