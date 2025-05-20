package com.trading.api.dto;

import com.trading.api.model.enums.TradeAssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for StockDim entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDimDTO {
    
    private UUID stockUuid;
    
    @NotNull(message = "Asset type is required")
    private TradeAssetType assetType;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Tick size is required")
    @Positive(message = "Tick size must be positive")
    private Double tickSize;
    
    @NotNull(message = "Tick value is required")
    @PositiveOrZero(message = "Tick value must be positive or zero")
    private Double tickValue;
    
    @NotNull(message = "Volume size is required")
    @Positive(message = "Volume size must be positive")
    private Double volumeSize;
}
