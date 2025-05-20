package com.trading.api.dto;

import com.trading.api.model.enums.TradeActionType;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for ActionDim entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionDimDTO {
    
    private UUID actionDimUuid;
    
    @NotNull(message = "Asset type is required")
    private TradeAssetType assetType;
    
    @NotNull(message = "Direction type is required")
    private TradeDirectionType directionType;
    
    @NotNull(message = "Action type is required")
    private TradeActionType actionType;
    
    @NotNull(message = "Volume is required")
    private Double volume;
}
