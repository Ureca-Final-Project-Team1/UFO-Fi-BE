package com.example.ufo_fi.domain.payment.domain;

import com.example.ufo_fi.domain.payment.infrastructure.toss.response.ConfirmResult;
import com.example.ufo_fi.domain.payment.presentation.dto.request.ConfirmReq;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StateMetaData {
    private final Map<MetaDataKey, Object> metaData = new HashMap<>();

    public <T> void put(MetaDataKey metaDataKey, T value) {
        metaData.put(metaDataKey, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(MetaDataKey metaDataKey, Class<T> clazz) {
        return (T) metaData.get(metaDataKey);
    }
}
