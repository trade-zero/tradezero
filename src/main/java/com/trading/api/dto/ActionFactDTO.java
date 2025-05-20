package com.trading.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for ActionFact entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionFactDTO {
    
    private UUID actionFactUuid;
    
    @NotNull(message = "Risk management UUID is required")
    private UUID riskManagementUuid;
    
    @NotNull(message = "Action dimension UUID is required")
    private UUID actionDimUuid;
    
    @NotNull(message = "Datetime ID is required")
    private Long datetimeId;
}
