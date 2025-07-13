package com.example.ufo_fi.domain.tradepost.controller;


import com.example.ufo_fi.domain.tradepost.dto.request.TradePostCreateReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostFilterReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostSearchReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostUpdateReq;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostCommonRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostFilterRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostSearchRes;
import com.example.ufo_fi.domain.tradepost.service.TradePostService;
import com.example.ufo_fi.global.response.ResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TradePostController {

    private final TradePostService tradePostService;

    @PostMapping("/posts")
    public ResponseEntity<ResponseBody<TradePostCommonRes>> createTradePost(
        @RequestBody @Valid TradePostCreateReq request,
        @RequestParam Long userId
    ) {

        return ResponseEntity.ok(
            ResponseBody.success(tradePostService.createTradePost(request, userId)));
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseBody<TradePostSearchRes>> readTradePosts(
        @ModelAttribute TradePostSearchReq request,
        @RequestParam Long userId
    ) {

        return ResponseEntity.ok(
            ResponseBody.success(tradePostService.readTradePostList(request, userId)));
    }

    @PostMapping("/posts/filter")
    public ResponseEntity<ResponseBody<TradePostFilterRes>> readFilterPost(
        @RequestBody TradePostFilterReq request,
        @RequestParam Long userId
    ) {

        return ResponseEntity.ok(
            ResponseBody.success(tradePostService.readFilterList(request, userId)));
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<ResponseBody<TradePostCommonRes>> updateTradePost(
        @PathVariable Long postId,
        @RequestBody @Valid TradePostUpdateReq request,
        @RequestParam Long userId
    ) {

        return ResponseEntity.ok(
            ResponseBody.success(tradePostService.updateTradePost(postId, request, userId)));
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
