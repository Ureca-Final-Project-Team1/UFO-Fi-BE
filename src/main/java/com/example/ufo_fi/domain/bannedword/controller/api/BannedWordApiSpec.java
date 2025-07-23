package com.example.ufo_fi.domain.bannedword.controller.api;

import com.example.ufo_fi.domain.bannedword.dto.request.BannedWordCreateReq;
import com.example.ufo_fi.domain.bannedword.dto.request.BannedWordDeleteBulkReq;
import com.example.ufo_fi.domain.bannedword.dto.request.BannedWordReadPageReq;
import com.example.ufo_fi.domain.bannedword.dto.response.BannedWordBulkDeleteRes;
import com.example.ufo_fi.domain.bannedword.dto.response.BannedWordCreateRes;
import com.example.ufo_fi.domain.bannedword.dto.response.BannedWordDeleteRes;
import com.example.ufo_fi.domain.bannedword.dto.response.BannedWordReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BannedWordApiSpec {

    @Operation(summary = "금칙어 등록", description = "금칙어 등록 : 관리자 로그인 필요")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("v1/admin/bannedword")
    ResponseEntity<ResponseBody<BannedWordCreateRes>> createBannedWord(
        @Valid @RequestBody BannedWordCreateReq request
    );

    @Operation(summary = "금칙어 전체 조회", description = "금칙어 전체 조회 : 관리자 로그인 필요")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("v1/admin/bannedword")
    ResponseEntity<ResponseBody<Page<BannedWordReadRes>>> readBandWords(
        @Valid @ParameterObject BannedWordReadPageReq request
    );

    @Operation(summary = "금칙어 단일 삭제", description = "금칙어 단일 삭제 : 관리자 로그인 필요")
    @ApiResponse(useReturnTypeSchema = true)
    @DeleteMapping("v1/admin/{banwordId}")
    ResponseEntity<ResponseBody<BannedWordDeleteRes>> deleteBanWord(
        @Parameter(description = "삭제할 금칙어 ID")
        @PathVariable("banwordId") Long id
    );

    @Operation(summary = "금칙어 일괄 삭제", description = "금칙어 일괄 삭제 : 관리자 로그인 필요")
    @ApiResponse(useReturnTypeSchema = true)
    @DeleteMapping("v1/admin/bannedword")
    ResponseEntity<ResponseBody<BannedWordBulkDeleteRes>> deleteBannedWords(
        @Parameter(description = "삭제할 금칙어 ID 목록")
        @RequestBody BannedWordDeleteBulkReq request
    );
}
