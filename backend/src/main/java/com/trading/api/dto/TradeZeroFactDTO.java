package com.trading.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for TradeZeroFact entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeZeroFactDTO {
    
    private UUID tradeZeroFactUuid;
    
    @NotNull(message = "Trade zero dimension UUID is required")
    private UUID tradeZeroDimUuid;
    
    @NotNull(message = "Agent dimension UUID is required")
    private UUID agentDimUuid;
    
    private Integer epoch;
    
    private Boolean trained;
}
