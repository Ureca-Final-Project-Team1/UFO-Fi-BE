package com.example.ufo_fi.domain.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class URes {
    private Long id;
    private String name;

    public static URes from(Long id, String name){
        return URes.builder()
                .id(id)
                .name(name)
                .build();
    }
}
