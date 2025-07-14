package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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

    private List<TradePostSearchDetailRes> posts;
    private boolean hasNext;
    private LocalDateTime nextCursor;
    private Long nextLastId;

    public static TradePostSearchRes of(Slice<TradePost> tradePosts) {

        List<TradePostSearchDetailRes> postDetails = tradePosts.getContent().stream()
            .map(TradePostSearchDetailRes::from)
            .collect(Collectors.toList());

        LocalDateTime nextCursor = null;
        Long nextLastId = null;

        if (!tradePosts.getContent().isEmpty()) {
            TradePost lastPost = tradePosts.getContent().get(tradePosts.getContent().size() - 1);
            nextCursor = lastPost.getCreatedAt();
            nextLastId = lastPost.getId();
        }

        return TradePostSearchRes.builder()
            .posts(postDetails)
            .hasNext(tradePosts.hasNext())
            .nextCursor(nextCursor)
            .nextLastId(nextLastId)
            .build();
    }
}
