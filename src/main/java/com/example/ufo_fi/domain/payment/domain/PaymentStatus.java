package com.example.ufo_fi.domain.payment.domain;

import com.example.ufo_fi.domain.payment.domain.state.DoneState;
import com.example.ufo_fi.domain.payment.domain.state.FailState;
import com.example.ufo_fi.domain.payment.domain.state.InProgressState;
import com.example.ufo_fi.domain.payment.domain.state.ReadyState;
import com.example.ufo_fi.domain.payment.domain.state.State;

public enum PaymentStatus {
    READY, IN_PROGRESS, DONE, FAIL, TIMEOUT;
}
