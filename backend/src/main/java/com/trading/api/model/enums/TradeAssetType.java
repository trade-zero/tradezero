package com.trading.api.model.enums;

/**
 * Enum representing trade asset types in the trading system.
 */
public enum TradeAssetType {
    WIN$("Mini Índice"),
    WDO$("Mini Dólar");

    private final String value;

    TradeAssetType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TradeAssetType fromValue(String value) {
        for (TradeAssetType type : TradeAssetType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown TradeAssetType: " + value);
    }
}
