package com.trading.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for OrderVenueDim entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVenueDimDTO {
    
    private UUID orderVenueDimUuid;
    
    @NotBlank(message = "Exchange is required")
    private String exchange;
    
    @NotBlank(message = "Broker is required")
    private String broker;
    
    @NotBlank(message = "Platform is required")
    private String platform;
}
