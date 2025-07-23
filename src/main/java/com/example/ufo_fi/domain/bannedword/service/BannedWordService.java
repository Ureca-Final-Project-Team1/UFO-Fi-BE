package com.example.ufo_fi.domain.bannedword.service;


import com.example.ufo_fi.domain.bannedword.dto.request.BannedWordCreateReq;
import com.example.ufo_fi.domain.bannedword.dto.request.BannedWordDeleteBulkReq;
import com.example.ufo_fi.domain.bannedword.dto.request.BannedWordReadPageReq;
import com.example.ufo_fi.domain.bannedword.dto.response.BannedWordBulkDeleteRes;
import com.example.ufo_fi.domain.bannedword.dto.response.BannedWordCreateRes;
import com.example.ufo_fi.domain.bannedword.dto.response.BannedWordDeleteRes;
import com.example.ufo_fi.domain.bannedword.dto.response.BannedWordReadRes;
import com.example.ufo_fi.domain.bannedword.entity.BannedWord;
import com.example.ufo_fi.domain.bannedword.exception.BannedWordErrorCode;
import com.example.ufo_fi.domain.bannedword.filter.BannedWordFilter;
import com.example.ufo_fi.domain.bannedword.repository.BannedWordRepository;
import com.example.ufo_fi.domain.bannedword.validator.BannedWordCommonValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BannedWordService {

    private final BannedWordRepository bannedWordRepository;
    private final BannedWordCommonValidator bannedWordCommonValidator;
    private final BannedWordFilter bannedWordFilter;

    @Transactional
    public BannedWordCreateRes createBannedWord(BannedWordCreateReq request) {

        BannedWord bannedWord = BannedWord.from(request);

        bannedWordCommonValidator.validateNotDuplicated(
            bannedWordRepository.existsByWord(bannedWord.getWord()),
            BannedWordErrorCode.DUPLICATED_BANNED_WORD);

        BannedWord savedBannedWord = bannedWordRepository.save(bannedWord);
        bannedWordFilter.reload();

        return BannedWordCreateRes.from(savedBannedWord);
    }

    public Page<BannedWordReadRes> readBannedWords(BannedWordReadPageReq bannedWordReadPageReq) {
        Pageable pageable = bannedWordReadPageReq.toPageable();
        return bannedWordRepository.findAll(pageable).map(BannedWordReadRes::from);
    }

    @Transactional
    public BannedWordDeleteRes deleteBannedWord(Long bannedWordId) {

        BannedWord bannedWord = bannedWordCommonValidator.getBannedWord(bannedWordId);
        bannedWordRepository.delete(bannedWord);
        bannedWordFilter.reload();

        return BannedWordDeleteRes.from(bannedWord);
    }

    @Transactional
    public BannedWordBulkDeleteRes deleteBanWordsByIds(BannedWordDeleteBulkReq request) {
        List<Long> ids = request.getIds();

        List<BannedWord> banWords = bannedWordRepository.findAllById(ids);

        bannedWordCommonValidator.validateAllIdsExist(ids, banWords,
            BannedWordErrorCode.BANNED_WORD_NOT_FOUND);

        bannedWordRepository.deleteAllInBatch(banWords);

        bannedWordFilter.reload();

        return BannedWordBulkDeleteRes.of(ids);
    }
}
