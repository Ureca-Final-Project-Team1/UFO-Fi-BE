package com.example.ufo_fi.v2.interestedpost.domain;

import com.example.ufo_fi.v2.plan.domain.Carrier;

import java.util.List;

/**
 * 관심 통신사 Bit값 변경을 위한 ENUM
 */
public enum InterestedCarriers {
    SKT(1), KT(2), LGU(4);

    private final int bit;

    InterestedCarriers(int bit) {
        this.bit = bit;
    }

    public int getBit() {
        return bit;
    }

    public static int encode(List<InterestedCarriers> carriers) {
        int bitmask = 0;
        for (InterestedCarriers carrier : carriers) {
            bitmask |= carrier.getBit();
        }
        return bitmask;
    }

    public static InterestedCarriers fromCarrier(Carrier carrier) {
        return switch (carrier) {
            case LGU -> LGU;
            case KT -> KT;
            case SKT -> SKT;
        };
    }
}
