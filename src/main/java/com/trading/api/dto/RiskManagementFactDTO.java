package com.trading.api.dto;

import com.trading.api.model.enums.TradeActionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for RiskManagementFact entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskManagementFactDTO {
    
    private UUID riskManagementUuid;
    
    @NotNull(message = "Trade zero fact UUID is required")
    private UUID tradeZeroFactUuid;
    
    @NotNull(message = "Actions count is required")
    @PositiveOrZero(message = "Actions count must be positive or zero")
    private Integer actions;
    
    @NotNull(message = "Valid inputs are required")
    private TradeActionType[] validInputs;
}
