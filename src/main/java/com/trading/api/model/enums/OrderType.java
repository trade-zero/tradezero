package com.trading.api.model.enums;

/**
 * Enum representing order types in the trading system.
 */
public enum OrderType {
    market("market"),
    limit("limit"),
    stop("stop");

    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OrderType fromValue(String value) {
        for (OrderType type : OrderType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown OrderType: " + value);
    }
}
