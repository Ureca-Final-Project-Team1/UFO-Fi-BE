package com.example.ufo_fi.domain.signup.service;

import com.example.ufo_fi.domain.nickname.entity.Nickname;
import com.example.ufo_fi.domain.nickname.repository.NicknameRepository;
import com.example.ufo_fi.domain.signup.exception.SignupErrorCode;
import com.example.ufo_fi.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * DB에서 랜덤한 ID를 뽑아와, 랜덤한 고유 닉네임을 만들어주는 클래스
 */
@Component
@RequiredArgsConstructor
public class RandomNicknameGenerator {
    private static final String ID_FORMAT = "%03d";
    private static final String BASIC_NAME = " 지구인 #";

    private final NicknameRepository nicknameRepository;
    private final AutoIncrementRandomIdSelector autoIncrementRandomIdSelector;

    /**
     * 랜덤한 닉네임을 생성한다.
     * 1. 사용가능한 닉네임 형용사의 Id를 랜덤하게 뽑아온다.
     * 2. FullNickname 형식으로 받는다.
     */
    public String generate() {
        long nicknameCount = nicknameRepository.count();
        if(nicknameCount == 0) throw new GlobalException(SignupErrorCode.NO_NICKNAME);

        long randomId = autoIncrementRandomIdSelector.select(nicknameCount);

        return getFullNickname(randomId);
    }

    //FullNickname 형식으로 받는다.
    private String getFullNickname(long randomId) {
        Nickname nickname = nicknameRepository.findById(randomId)
                .orElseThrow(() -> new GlobalException(SignupErrorCode.NO_NICKNAME));

        String rawNickname = nickname.getNicknameAdjective();
        String formattedId = String.format(ID_FORMAT, randomId);

        return rawNickname + BASIC_NAME + formattedId;
    }
}
