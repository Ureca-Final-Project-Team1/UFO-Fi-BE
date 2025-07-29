package com.example.ufo_fi.domain.payment.domain.slack;

import com.example.ufo_fi.domain.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.global.log.LogTrace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SlackEvent {
    private LogTrace logTrace;
    private StateMetaData stateMetaData;
}
