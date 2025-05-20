package com.trading.api.dto;

import com.trading.api.model.enums.OrderType;
import com.trading.api.model.enums.TradeActionType;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for OrderDim entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDimDTO {
    
    private UUID orderDimUuid;
    
    @NotNull(message = "Order type is required")
    private OrderType orderType;
    
    @NotNull(message = "Direction type is required")
    private TradeDirectionType directionType;
    
    @NotNull(message = "Action type is required")
    private TradeActionType actionType;
    
    @NotNull(message = "Asset type is required")
    private TradeAssetType assetType;
    
    @NotNull(message = "Volume is required")
    @Positive(message = "Volume must be positive")
    private Double volume;
}
