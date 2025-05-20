package com.trading.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for AgentDim entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentDimDTO {
    
    private UUID agentDimUuid;
    
    @NotBlank(message = "Name is required")
    private String name;
}
