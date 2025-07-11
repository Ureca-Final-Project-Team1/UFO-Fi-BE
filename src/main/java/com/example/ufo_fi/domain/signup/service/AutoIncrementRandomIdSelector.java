package com.example.ufo_fi.domain.signup.service;

import com.example.ufo_fi.domain.signup.exception.SignupErrorCode;
import com.example.ufo_fi.global.exception.GlobalException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * PK가 AutoIncrement라고 가정했을 때,
 */
@Component
public class AutoIncrementRandomIdSelector {

    public long select(long pkCount){
        if(pkCount < 0) throw new GlobalException(SignupErrorCode.RANDOM_NUMBER_ERROR);
        return ThreadLocalRandom.current().nextLong(1, pkCount + 1);
    }
}
