package com.trading.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for BalanceFact entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceFactDTO {
    
    private UUID balanceUuid;
    
    @NotNull(message = "Portfolio UUID is required")
    private UUID portfolioUuid;
    
    @NotNull(message = "Datetime ID is required")
    private Long datetimeId;
    
    @NotNull(message = "Initial balance is required")
    @PositiveOrZero(message = "Initial balance must be positive or zero")
    private Double initial;
    
    @NotNull(message = "Current balance is required")
    @PositiveOrZero(message = "Current balance must be positive or zero")
    private Double current;
    
    @NotNull(message = "Max balance is required")
    @PositiveOrZero(message = "Max balance must be positive or zero")
    private Double max;
    
    @NotNull(message = "Min balance is required")
    @PositiveOrZero(message = "Min balance must be positive or zero")
    private Double min;
}
