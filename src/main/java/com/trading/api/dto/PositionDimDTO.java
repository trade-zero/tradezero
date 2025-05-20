package com.trading.api.dto;

import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for PositionDim entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDimDTO {
    
    private UUID positionDimUuid;
    
    @NotNull(message = "Asset type is required")
    private TradeAssetType assetType;
    
    @NotNull(message = "Direction type is required")
    private TradeDirectionType directionType;
    
    private Double value;
}
