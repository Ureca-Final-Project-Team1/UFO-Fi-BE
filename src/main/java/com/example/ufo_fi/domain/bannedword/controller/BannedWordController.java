package com.example.ufo_fi.domain.bannedword.controller;

import com.example.ufo_fi.domain.bannedword.controller.api.BannedWordApiSpec;
import com.example.ufo_fi.domain.bannedword.dto.request.BannedWordCreateReq;
import com.example.ufo_fi.domain.bannedword.dto.request.BannedWordDeleteBulkReq;
import com.example.ufo_fi.domain.bannedword.dto.request.BannedWordReadPageReq;
import com.example.ufo_fi.domain.bannedword.dto.response.BannedWordBulkDeleteRes;
import com.example.ufo_fi.domain.bannedword.dto.response.BannedWordCreateRes;
import com.example.ufo_fi.domain.bannedword.dto.response.BannedWordDeleteRes;
import com.example.ufo_fi.domain.bannedword.dto.response.BannedWordReadRes;
import com.example.ufo_fi.domain.bannedword.service.BannedWordService;
import com.example.ufo_fi.global.response.ResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BannedWordController implements BannedWordApiSpec {

    private final BannedWordService bannedWordService;

    @Override
    public ResponseEntity<ResponseBody<BannedWordCreateRes>> createBannedWord(
        BannedWordCreateReq request) {

        return ResponseEntity.ok(
            ResponseBody.success(
                bannedWordService.createBannedWord(request)
            )
        );
    }

    @Override
    public ResponseEntity<ResponseBody<Page<BannedWordReadRes>>> readBannedWords(
        @Valid BannedWordReadPageReq bannedWordReadPageReq) {

        return ResponseEntity.ok(
            ResponseBody.success(
                bannedWordService.readBannedWords(bannedWordReadPageReq)
            )
        );
    }

    @Override
    public ResponseEntity<ResponseBody<BannedWordDeleteRes>> deleteBannedWord(Long id) {

        return ResponseEntity.ok(
            ResponseBody.success(
                bannedWordService.deleteBannedWord(id)
            )
        );
    }

    @Override
    public ResponseEntity<ResponseBody<BannedWordBulkDeleteRes>> deleteBannedWords(
        BannedWordDeleteBulkReq request) {

        return ResponseEntity.ok(
            ResponseBody.success(
                bannedWordService.deleteBanWordsByIds(request)
            )
        );
    }
}
