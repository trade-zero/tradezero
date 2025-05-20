package com.trading.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for RiskMetricsFact entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskMetricsFactDTO {
    
    private UUID riskMetricsUuid;
    
    @NotNull(message = "Risk management UUID is required")
    private UUID riskManagementUuid;
    
    @NotNull(message = "Datetime ID is required")
    private Long datetimeId;
    
    @NotNull(message = "Margin used is required")
    @PositiveOrZero(message = "Margin used must be positive or zero")
    private Double marginUsed;
    
    @NotNull(message = "Max drawdown is required")
    @Min(value = 0, message = "Max drawdown must be between 0 and 1")
    @Max(value = 1, message = "Max drawdown must be between 0 and 1")
    private Double maxDrawdown;
    
    @NotNull(message = "Sharpe ratio is required")
    private Double sharpeRatio;
}
