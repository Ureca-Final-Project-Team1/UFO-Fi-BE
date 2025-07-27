package com.example.ufo_fi.domain.payment.domain;

import com.example.ufo_fi.domain.payment.domain.state.State;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStateContext {
    private final Map<PaymentStatus, State<?>> states;

    public PaymentStateContext(List<State<?>> paymentStates){
        this.states = paymentStates.stream()
                .collect(Collectors.toMap(State::status, paymentState -> paymentState));
    }

    @SuppressWarnings("unchecked")
    public <T> void proceed(Payment payment, T param) {
        State<T> state = (State<T>) states.get(payment.getStatus());
        state.proceed(payment, param);
    }

    public <T> void proceedAll(Payment payment, T param) {
        while (!isTerminal(payment)) {
            proceed(payment, param);
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
