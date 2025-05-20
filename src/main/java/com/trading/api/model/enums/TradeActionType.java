package com.trading.api.model.enums;

/**
 * Enum representing trade action types in the trading system.
 */
public enum TradeActionType {
    hold("hold"),
    open("open"),
    close("close");

    private final String value;

    TradeActionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TradeActionType fromValue(String value) {
        for (TradeActionType type : TradeActionType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown TradeActionType: " + value);
    }
}
