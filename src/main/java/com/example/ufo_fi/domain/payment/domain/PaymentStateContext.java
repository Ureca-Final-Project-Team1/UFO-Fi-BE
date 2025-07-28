package com.example.ufo_fi.domain.payment.domain;

import com.example.ufo_fi.domain.payment.domain.state.State;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStateContext {
    private final Map<PaymentStatus, State> states;

    @Autowired
    public PaymentStateContext(List<State> paymentStates){
        this.states = paymentStates.stream()
                .collect(Collectors.toMap(State::status, paymentState -> paymentState));
    }

    @SuppressWarnings("unchecked")
    public void proceed(Payment payment, StateMetaData stateMetaData) {
        State state = states.get(payment.getStatus());
        state.proceed(payment, stateMetaData);
    }

    public <T> void proceedAll(Payment payment, StateMetaData stateMetaData) {
        while (!isTerminal(payment)) {
            proceed(payment, stateMetaData);
        }
    }

    private boolean isTerminal(Payment payment) {
        return isFail(payment) || isDone(payment);
    }


    private boolean isFail(Payment payment){
        return payment.getStatus().equals(PaymentStatus.FAIL);
    }

    private boolean isDone(Payment payment){
        return payment.getStatus().equals(PaymentStatus.DONE);
    }
}
