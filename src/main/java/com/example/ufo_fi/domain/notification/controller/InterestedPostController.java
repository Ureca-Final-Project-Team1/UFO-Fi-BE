package com.example.ufo_fi.domain.notification.controller;

import com.example.ufo_fi.domain.notification.controller.api.InterestedPostApiSpec;
import com.example.ufo_fi.domain.notification.dto.request.InterestedPostUpdateReq;
import com.example.ufo_fi.domain.notification.service.InterestedPostService;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InterestedPostController implements InterestedPostApiSpec {

    private final InterestedPostService interestedPostService;

    // PatchMapping, 관심 상품 조건 업데이트
    @Override
    public ResponseEntity<ResponseBody<Void>> updateInterestedPost(
            InterestedPostUpdateReq request,
            Long userId) {

        interestedPostService.updateInterestedPost(userId, request);
        return ResponseEntity.ok(ResponseBody.noContent());
    }
}
