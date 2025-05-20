package com.trading.api.dto;

import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeTimeFrameType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for CandlestickFact entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandlestickFactDTO {
    
    @NotNull(message = "Data feed UUID is required")
    private UUID dataFeedUuid;
    
    @NotNull(message = "Trade asset is required")
    private TradeAssetType tradeAsset;
    
    @NotNull(message = "Trade time frame is required")
    private TradeTimeFrameType tradeTimeFrame;
    
    @NotNull(message = "Datetime ID is required")
    private Long datetimeId;
    
    @NotNull(message = "Open price is required")
    private Double open;
    
    @NotNull(message = "High price is required")
    private Double high;
    
    @NotNull(message = "Low price is required")
    private Double low;
    
    @NotNull(message = "Close price is required")
    private Double close;
    
    @NotNull(message = "Volume is required")
    private Double volume;
}
