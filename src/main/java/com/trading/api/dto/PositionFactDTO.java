package com.trading.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for PositionFact entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionFactDTO {
    
    private UUID positionUuid;
    
    @NotNull(message = "Portfolio UUID is required")
    private UUID portfolioUuid;
    
    @NotNull(message = "Position dimension UUID is required")
    private UUID positionDimUuid;
    
    @NotNull(message = "Datetime ID is required")
    private Long datetimeId;
    
    @NotNull(message = "Entry price is required")
    @Positive(message = "Entry price must be positive")
    private Double entryPrice;
}
