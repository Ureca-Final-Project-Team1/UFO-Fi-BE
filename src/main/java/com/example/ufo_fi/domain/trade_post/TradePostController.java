package com.example.ufo_fi.domain.trade_post;


import com.example.ufo_fi.domain.user.User;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TradePostController {

    private final TradePostService tradePostService;

    @PostMapping("/posts")
    public ResponseBody<TradePostCommonResponse> saveTradePost(
        @RequestBody TradePostCreateRequest request,
        User user) {

        return ResponseBody.success(tradePostService.save(request, user));
    }

}
