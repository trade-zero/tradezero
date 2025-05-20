package com.trading.api.model;

import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeTimeFrameType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the candlestick_fact table in the database.
 * This table is partitioned by trade_time_frame.
 */
@Entity
@Table(name = "candlestick_fact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandlestickFact {

    @EmbeddedId
    private CandlestickFactId id;

    @Column(name = "open", nullable = false)
    private Double open;

    @Column(name = "high", nullable = false)
    private Double high;

    @Column(name = "low", nullable = false)
    private Double low;

    @Column(name = "close", nullable = false)
    private Double close;

    @Column(name = "volume", nullable = false)
    private Double volume;

    /**
     * Composite primary key for CandlestickFact.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CandlestickFactId {
        
        @Column(name = "data_feed_uuid", nullable = false)
        private UUID dataFeedUuid;
        
        @Enumerated(EnumType.STRING)
        @Column(name = "trade_asset", nullable = false)
        private TradeAssetType tradeAsset;
        
        @Enumerated(EnumType.STRING)
        @Column(name = "trade_time_frame", nullable = false)
        private TradeTimeFrameType tradeTimeFrame;
        
        @Column(name = "datetime_id", nullable = false)
        private Long datetimeId;
    }
}
