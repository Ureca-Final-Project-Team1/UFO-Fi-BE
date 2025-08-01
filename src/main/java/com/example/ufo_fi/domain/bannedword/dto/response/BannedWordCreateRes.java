package com.example.ufo_fi.domain.bannedword.dto.response;

import com.example.ufo_fi.domain.bannedword.entity.BannedWord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "금칙어 생성 응답 DTO")
public class BannedWordCreateRes {

    @Schema(description = "금칙어 ID", example = "1")
    private Long id;

    @Schema(description = "금칙어 문자열", example = "시발")
    private String word;

    public static BannedWordCreateRes from(final BannedWord bannedWord) {
        return BannedWordCreateRes.builder()
            .id(bannedWord.getId())
            .word(bannedWord.getWord())
            .build();
    }
}
