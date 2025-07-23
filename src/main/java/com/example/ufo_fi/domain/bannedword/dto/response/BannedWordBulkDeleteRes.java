package com.example.ufo_fi.domain.bannedword.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "금칙어 다중 삭제 응답 DTO")
public class BannedWordBulkDeleteRes {

    @Schema(description = "삭제된 금칙어 ID 목록", example = "[1, 2, 3]")
    private List<Long> deletedIds;

    @Schema(description = "삭제된 금칙어 개수", example = "3")
    private int deletedCount;

    public static BannedWordBulkDeleteRes from(List<Long> ids) {
        return BannedWordBulkDeleteRes.builder()
            .deletedIds(ids)
            .deletedCount(ids.size())
            .build();
    }
}
