package com.example.ufo_fi.domain.bannedword.validator;

import com.example.ufo_fi.domain.bannedword.entity.BannedWord;
import com.example.ufo_fi.domain.bannedword.exception.BannedWordErrorCode;
import com.example.ufo_fi.domain.bannedword.repository.BannedWordRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BannedWordCommonValidator {

    private final BannedWordRepository bannedWordRepository;

    public void validateNotDuplicated(boolean exists, BannedWordErrorCode errorCode) {
        if (exists) {
            throw new GlobalException(errorCode);
        }
    }

    public void validateAllIdsExist(List<Long> ids, List<BannedWord> entities,
        BannedWordErrorCode errorCode) {

        Set<Long> foundIds = entities.stream().map(BannedWord::getId).collect(Collectors.toSet());

        if (!foundIds.containsAll(ids)) {
            throw new GlobalException(errorCode);
        }
    }

    public BannedWord getBannedWord(Long id) {
        return bannedWordRepository.findById(id)
            .orElseThrow(() -> new GlobalException(BannedWordErrorCode.BANNED_WORD_NOT_FOUND));
    }
}
