package com.trading.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for PortfolioFact entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioFactDTO {
    
    private UUID portfolioUuid;
    
    @NotNull(message = "Trade zero fact UUID is required")
    private UUID tradeZeroFactUuid;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private String description;
}
