package com.example.ufo_fi.global.security.oauth;

import com.example.ufo_fi.domain.user.entity.Role;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.security.oauth.provider.OAuth2Response;
import com.example.ufo_fi.global.security.principal.CustomOAuth2User;
import com.example.ufo_fi.global.security.refresh.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final RefreshRepository refreshRepository;

    /**
     * 1. DefaultOAuth2User를 받아옴(DefaultOAuth2UserService에서 Access 토큰을 받아오고, 그것으로 카카오 유저 서비스에 접근해 정보 또한 받아온다.)
     * 2. OAuth2Response => 모든 OAuth 인증 추상 객체
     * 3. kakao 계정 id로 User를 찾아온다.
     * 4. 분기
     * 4-1. 만약 유저가 존재한다면, 로그인한다.
     * 4-2. 만약 유저가 존재하지 않는다면, 회원가입을 한다.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); //1
        OAuth2Response oAuth2Response = OAuth2Response.of(userRequest.getClientRegistration(), oAuth2User);  //2
        User existUser = userRepository.findByKakaoId(oAuth2Response.getProviderId());  //3
        if(existUser != null) return login(existUser);  //4-1
        return signup(oAuth2Response);  //4-2
    }

    /*
     * 로그인한다.
     * 1. refresh 토큰이 있다면 제거한다.
     * 2. OAuth2User를 생성한다.(후에 OAuth2Provider에서 OAuth2AuthenticationToken에 들어간다.
     * **OAuth2로 로그인할 경우 CustomOAuth2User를 @AuthenticationPrincipal로 사용가능하다.
     */
    private OAuth2User login(User existUser) {
        refreshRepository.deleteByUser(existUser); // 기존 refresh 토큰 제거, 없어도 예외 X
        return CustomOAuth2User.from(existUser);
    }

    /*
     * 회원가입한다.
     * 1. 첫 회원가입은 ROLE_NO_INFO로 한다.
     * 2. 저장 후 OAuth2User를 생성 반환한다.
     */
    private OAuth2User signup(OAuth2Response oAuth2Response) {
        User newUser = User.of(oAuth2Response, Role.ROLE_NO_INFO);
        User signupUser = userRepository.save(newUser);
        return CustomOAuth2User.from(signupUser);
    }
}
