package com.trading.api.model.enums;

/**
 * Enum representing trade time frame types in the trading system.
 */
public enum TradeTimeFrameType {
    m1("m1"),
    m5("m5"),
    m15("m15"),
    m30("m30"),
    H1("H1"),
    H4("H4"),
    D1("D1"),
    W1("W1");

    private final String value;

    TradeTimeFrameType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TradeTimeFrameType fromValue(String value) {
        for (TradeTimeFrameType type : TradeTimeFrameType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown TradeTimeFrameType: " + value);
    }
}
