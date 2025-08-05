package com.example.ufo_fi.v2.payment.domain.payment.state.failstrategy;

import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TossErrorHandleStrategyContext {

    private final Map<TossErrorStrategy, TossErrorHandleStrategy> errorHandleStrategies;

    @Autowired
    public TossErrorHandleStrategyContext(
        List<TossErrorHandleStrategy> tossErrorHandleStrategies
    ) {
        this.errorHandleStrategies = tossErrorHandleStrategies.stream()
            .collect(Collectors.toMap(
                TossErrorHandleStrategy::tossErrorStrategy,
                tossErrorHandleStrategy -> tossErrorHandleStrategy)
            );
    }

    public void process(Payment payment, String rawTossErrorCode, StateMetaData stateMetaData){
        TossErrorCode tossErrorCode = TossErrorCode.valueOf(rawTossErrorCode);
        TossErrorStrategy tossErrorStrategy = tossErrorCode.getTossErrorStrategy();

        TossErrorHandleStrategy tossErrorHandleStrategy = errorHandleStrategies.get(tossErrorStrategy);

        tossErrorHandleStrategy.process(payment, tossErrorCode, stateMetaData);
    }
}
