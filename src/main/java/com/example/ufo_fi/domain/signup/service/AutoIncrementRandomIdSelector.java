package com.example.ufo_fi.domain.signup.service;

import org.springframework.stereotype.Component;

/**
 * PK가 AutoIncrement라고 가정했을 때,
 */
@Component
public class AutoIncrementRandomIdSelector {

    public long select(long pkCount){
        return (long) (Math.random() * pkCount) + 1;
    }
}
