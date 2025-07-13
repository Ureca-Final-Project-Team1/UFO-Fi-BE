package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradePostSearchRes {

    private Slice<TradePostSearchDetailRes> posts;
    private LocalDateTime nextCursor;
    private Long nextLastId;

    public static TradePostSearchRes of(Slice<TradePost> tradePosts, LocalDateTime nextCursor,
        Long nextLastId) {

        return TradePostSearchRes.builder()
            .posts(tradePosts.map(TradePostSearchDetailRes::from))
            .nextCursor(nextCursor)
            .nextLastId(nextLastId)
            .build();
    }
}
