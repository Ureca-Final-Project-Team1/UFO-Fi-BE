package com.example.ufo_fi.domain.tradepost.dto.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class TradePostSearchReq {

    private LocalDateTime cursor;
    private int size = 10; //기본 사이즈 10개로 일단 합니다.

    public LocalDateTime getCursorOrDefault() {
        if (cursor == null) {
            return LocalDateTime.now();
        }
        return cursor;
    }
}
