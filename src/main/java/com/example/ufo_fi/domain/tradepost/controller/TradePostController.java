package com.example.ufo_fi.domain.tradepost.controller;


import com.example.ufo_fi.domain.tradepost.dto.request.TradePostCreateReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostSearchReq;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostCommonRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostSearchRes;
import com.example.ufo_fi.domain.tradepost.service.TradePostService;
import com.example.ufo_fi.global.response.ResponseBody;
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
    public ResponseEntity<ResponseBody<TradePostCommonRes>> saveTradePost(
        @RequestBody TradePostCreateReq request,
        @RequestParam Long userId
    ) {
        return ResponseEntity.ok()
            .body(ResponseBody.success(tradePostService.save(request, userId)));
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseBody<TradePostSearchRes>> getTradePosts(
        @ModelAttribute TradePostSearchReq request, // Request param을 안쓴 이유는 이미 바인딩을 시켜놓음
        @RequestParam Long userId
    ) {
        return ResponseEntity.ok()
            .body(ResponseBody.success(tradePostService.getTradePostList(userId, request)));
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<ResponseBody<TradePostCommonRes>> updateTradePost(
        @RequestBody TradePostCreateReq request,
        @RequestParam Long userId
    ) {
        return ResponseEntity.ok()
            .body(ResponseBody.success(tradePostService.save(request, userId)));

    }
    
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ResponseBody<TradePostCommonRes>> deleteTradePost(
        @PathVariable Long postId,
        @RequestParam Long userId
    ) {
//        return ResponseEntity.noContent()
//            .body(ResponseBody.success(tradePostService.delete(postId, userId)));

        return ResponseEntity.ok()
            .body(ResponseBody.success(tradePostService.delete(postId, userId)));
    }


}
