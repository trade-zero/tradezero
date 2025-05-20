package com.trading.api.model.enums;

/**
 * Enum representing order status types in the trading system.
 */
public enum OrderStatusType {
    pending("pending"),
    filled("filled"),
    canceled("canceled"),
    rejected("rejected"),
    partially_filled("partially_filled");

    private final String value;

    OrderStatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OrderStatusType fromValue(String value) {
        for (OrderStatusType type : OrderStatusType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown OrderStatusType: " + value);
    }
}
