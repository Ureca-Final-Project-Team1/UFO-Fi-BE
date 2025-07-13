package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
public class TradePostFilterRes {

    private final List<TradePostFilterDetailRes> posts;
    private final boolean hasNext;

    private final LocalDateTime nextCursorCreatedAt;
    private final Long nextCursorId;

    private TradePostFilterRes(List<TradePostFilterDetailRes> posts, boolean hasNext,
        LocalDateTime nextCursorCreatedAt, Long nextCursorId) {

        this.posts = posts;
        this.hasNext = hasNext;
        this.nextCursorCreatedAt = nextCursorCreatedAt;
        this.nextCursorId = nextCursorId;
    }

    public static TradePostFilterRes from(Slice<TradePost> tradePosts) {

        List<TradePostFilterDetailRes> postDetails = tradePosts.getContent().stream()
            .map(TradePostFilterDetailRes::from)
            .collect(Collectors.toList());

        boolean hasNext = tradePosts.hasNext();
        LocalDateTime nextCursorCreatedAt = null;
        Long nextCursorId = null;
        
        if (!postDetails.isEmpty()) {
            TradePostFilterDetailRes lastPost = postDetails.get(postDetails.size() - 1);
            nextCursorCreatedAt = lastPost.getCreatedAt();
            nextCursorId = lastPost.getPostId();
        }

        return new TradePostFilterRes(postDetails, hasNext, nextCursorCreatedAt, nextCursorId);
    }
}