package com.example.ufo_fi.domain.tradepost.controller;


import com.example.ufo_fi.domain.tradepost.dto.request.TradePostCreateReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostSearchReq;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostCommonRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostSearchRes;
import com.example.ufo_fi.domain.tradepost.service.TradePostService;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "판매 게시글 관련 API")
@RestController
@RequiredArgsConstructor
public class TradePostController {

    private final TradePostService tradePostService;

    @PostMapping("/posts")
    @Operation(summary = "판매 게시글 생성", description = "판매 게시글을 생성합니다.")
    public ResponseEntity<ResponseBody<TradePostCommonRes>> createTradePost(
            @RequestBody TradePostCreateReq request,
            @Parameter(description = "판매자 ID", example = "1") @RequestParam Long userId
    ) {
        return ResponseEntity.ok()
                .body(ResponseBody.success(tradePostService.createTradePost(request, userId)));
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseBody<TradePostSearchRes>> readTradePosts(
            @ModelAttribute TradePostSearchReq request, // Request param을 안쓴 이유는 이미 바인딩을 시켜놓음
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok()
                .body(ResponseBody.success(tradePostService.readTradePostList(userId, request)));
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<ResponseBody<TradePostCommonRes>> updateTradePost(
            @RequestBody TradePostCreateReq request,
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok()
                .body(ResponseBody.success(tradePostService.createTradePost(request, userId)));

    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ResponseBody<TradePostCommonRes>> deleteTradePost(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {

        return ResponseEntity.ok()
                .body(ResponseBody.success(tradePostService.deleteTradePost(postId, userId)));
    }


}
