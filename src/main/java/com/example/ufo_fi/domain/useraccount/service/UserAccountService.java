package com.example.ufo_fi.domain.useraccount.service;

import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.domain.useraccount.dto.request.AccountCreateReq;
import com.example.ufo_fi.domain.useraccount.dto.response.AccountCreateRes;
import com.example.ufo_fi.domain.useraccount.dto.response.AccountReadRes;
import com.example.ufo_fi.domain.useraccount.entity.UserAccount;
import com.example.ufo_fi.domain.useraccount.exception.UserAccountErrorCode;
import com.example.ufo_fi.domain.useraccount.repository.UserAccountRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 계좌 도메인 서비스
 */
@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    /**
     * 유저의 계좌를 읽어온다.
     * 1. 없을 시 예외를 반환한다.
     * 2. 은행명, 계좌번호
     */
    public AccountReadRes readAccount(Long userId) {
        User user = userRepository.getReferenceById(userId);
        UserAccount userAccount = userAccountRepository.findByUser(user)
                .orElseThrow(() -> new GlobalException(UserAccountErrorCode.NO_USER_ACCOUNT));

        return AccountReadRes.from(userAccount);
    }

    /**
     * 유저의 계좌를 등록한다.
     * 1. 이미 등록되었을 시 예외를 반환한다.
     * 2. 은행명, 계좌번호, 비밀 번호를 받는다.
     */
    public AccountCreateRes createAccount(Long userId, AccountCreateReq accountCreateReq) {
        User user = userRepository.getReferenceById(userId);
        if(userAccountRepository.existsByUser(user)){
            throw new GlobalException(UserAccountErrorCode.ALREADY_ACCOUNT_EXIST);
        }

        UserAccount userAccount = UserAccount.of(user, accountCreateReq);
        userAccountRepository.save(userAccount);

        return AccountCreateRes.from(userAccount);
    }
}
