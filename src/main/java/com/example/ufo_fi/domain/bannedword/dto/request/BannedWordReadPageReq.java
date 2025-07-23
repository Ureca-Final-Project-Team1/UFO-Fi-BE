package com.example.ufo_fi.domain.bannedword.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannedWordReadPageReq {

    @Schema(description = "페이지 번호", example = "0")
    private int page = 0;

    @Schema(description = "페이지 크기", example = "10")
    private int size = 10;

    public Pageable toPageable() {
        return PageRequest.of(page, size, Sort.by(Direction.ASC, "id"));
    }

}

