package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradePostSearchRes {

    private List<TradePostSearchDetailRes> posts;
    private LocalDateTime nextCursor;

    public static TradePostSearchRes of(List<TradePost> tradePosts, LocalDateTime nextCursor) {
        return TradePostSearchRes.builder()
            .posts(tradePosts.stream()
                .map(TradePostSearchDetailRes::from)
                .toList())
            .nextCursor(nextCursor)
            .build();
    }
}
