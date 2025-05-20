package com.trading.api.model.enums;

/**
 * Enum representing trade direction types in the trading system.
 */
public enum TradeDirectionType {
    LONG("long"),
    SHORT("short"),
    WAIT("wait");

    private final String value;

    TradeDirectionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TradeDirectionType fromValue(String value) {
        for (TradeDirectionType type : TradeDirectionType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown TradeDirectionType: " + value);
    }
}
