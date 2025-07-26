package com.example.ufo_fi.domain.payment.entity;

import com.example.ufo_fi.domain.payment.state.*;

public enum PaymentStatus {
    CREATE, READY, IN_PROGRESS, DONE, FAIL;

    public State getState() {
        return switch (this) {
            case CREATE -> new EmptyState();
            case READY -> new ReadyState();
            case IN_PROGRESS -> new InProgressState();
            case DONE -> new DoneState();
            case FAIL -> new FailState();
        };
    }
}
