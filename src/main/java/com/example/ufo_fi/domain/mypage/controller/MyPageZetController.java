package com.example.ufo_fi.domain.mypage.controller;

import com.example.ufo_fi.domain.mypage.controller.api.MyPageZetApiSpec;
import com.example.ufo_fi.domain.user.dto.request.ZetPurchaseReq;
import com.example.ufo_fi.domain.user.dto.response.ZetPurchaseRes;
import com.example.ufo_fi.domain.user.service.UserService;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageZetController implements MyPageZetApiSpec {
    private final UserService userService;

    @Override
    public ResponseEntity<ResponseBody<ZetPurchaseRes>> purchaseZet(Long userId, ZetPurchaseReq zetPurchaseReq) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        userService.updateZet(userId, zetPurchaseReq)));
    }
}
