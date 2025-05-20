package com.trading.api.dto;

import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeTimeFrameType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for TradeZeroDim entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeZeroDimDTO {
    
    private UUID tradeZeroDimUuid;
    
    @NotNull(message = "Trade asset is required")
    private TradeAssetType[] tradeAsset;
    
    @NotNull(message = "Trade time frame is required")
    private TradeTimeFrameType tradeTimeFrame;
    
    @NotNull(message = "Balance initial is required")
    @Positive(message = "Balance initial must be positive")
    private Double balanceInitial;
    
    @NotNull(message = "Drawdown is required")
    @Min(value = 0, message = "Drawdown must be between 0 and 1")
    @Max(value = 1, message = "Drawdown must be between 0 and 1")
    private Double drawdown;
    
    @NotNull(message = "Max volume is required")
    @Positive(message = "Max volume must be positive")
    private Double maxVolume;
    
    @NotNull(message = "Max hold is required")
    @Positive(message = "Max hold must be positive")
    private Integer maxHold;
    
    @NotNull(message = "Look back is required")
    @Min(value = 1, message = "Look back must be between 1 and 540")
    @Max(value = 540, message = "Look back must be between 1 and 540")
    private Integer lookBack;
    
    @NotNull(message = "Look forward is required")
    @Min(value = 1, message = "Look forward must be between 1 and 540")
    @Max(value = 540, message = "Look forward must be between 1 and 540")
    private Integer lookForward;
    
    @NotNull(message = "Back propagate size is required")
    @Min(value = 1, message = "Back propagate size must be between 1 and 108")
    @Max(value = 108, message = "Back propagate size must be between 1 and 108")
    private Integer backPropagateSize;
    
    @NotNull(message = "Max episode is required")
    @Positive(message = "Max episode must be positive")
    private Integer maxEpisode;
}
