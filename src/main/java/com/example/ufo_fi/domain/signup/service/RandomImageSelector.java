package com.example.ufo_fi.domain.signup.service;

import com.example.ufo_fi.domain.profilephoto.entity.ProfilePhoto;
import com.example.ufo_fi.domain.profilephoto.repository.ProfilePhotoRepository;
import com.example.ufo_fi.domain.signup.exception.SignupErrorCode;
import com.example.ufo_fi.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RandomImageSelector {
    private final ProfilePhotoRepository profilePhotoRepository;
    private final AutoIncrementRandomIdSelector autoIncrementRandomIdSelector;

    /**
     * 랜덤 PK를 뽑아서 사진 URL을 가져오는 메서드
     * 1. ProfilePhoto 테이블의 전체 레코드 갯수 구해오기
     *  => 0일 시 예외
     * 2. 범위 내 랜덤 id 가져오기
     * 3. 그 랜덤 id로 사진URL 가져오기
     */
    public ProfilePhoto select() {
        long photoCount = profilePhotoRepository.count();
        if(photoCount == 0) throw new GlobalException(SignupErrorCode.NO_PHOTO);

        long randomId = autoIncrementRandomIdSelector.select(photoCount);

        return profilePhotoRepository.findById(randomId)
                .orElseThrow(() -> new GlobalException(SignupErrorCode.NO_PHOTO));
    }
}
