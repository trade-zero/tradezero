package com.trading.api.dto;

import com.trading.api.model.enums.OrderStatusType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for OrderFact entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFactDTO {
    
    private UUID orderFactUuid;
    
    @NotNull(message = "Order dimension UUID is required")
    private UUID orderDimUuid;
    
    @NotNull(message = "Order venue dimension UUID is required")
    private UUID orderVenueDimUuid;
    
    @NotNull(message = "Datetime ID is required")
    private Long datetimeId;
    
    @NotNull(message = "Portfolio UUID is required")
    private UUID portfolioUuid;
    
    @NotNull(message = "Order status is required")
    private OrderStatusType orderStatus;
    
    private Double executedPrice;
    
    private Double limitPrice;
    
    private Double stopPrice;
    
    @PositiveOrZero(message = "Fees must be positive or zero")
    private Double fees;
    
    private Double slippage;
    
    @PositiveOrZero(message = "Latency must be positive or zero")
    private Integer latencyMs;
}
