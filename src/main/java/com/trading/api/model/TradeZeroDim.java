package com.trading.api.model;

import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeTimeFrameType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.UUID;

/**
 * Entity representing the trade_zero_dim table in the database.
 */
@Entity
@Table(name = "trade_zero_dim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeZeroDim {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trade_zero_dim_uuid")
    private UUID tradeZeroDimUuid;

    @Column(name = "trade_asset", nullable = false, columnDefinition = "trade_asset_type[]")
    private TradeAssetType[] tradeAsset;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_time_frame", nullable = false)
    private TradeTimeFrameType tradeTimeFrame;

    @Column(name = "balance_initial", nullable = false)
    private Double balanceInitial;

    @Column(name = "drawdown", nullable = false)
    private Double drawdown;

    @Column(name = "max_volume", nullable = false)
    private Double maxVolume;

    @Column(name = "max_hold", nullable = false)
    private Integer maxHold;

    @Column(name = "look_back", nullable = false)
    private Integer lookBack;

    @Column(name = "look_forward", nullable = false)
    private Integer lookForward;

    @Column(name = "back_propagate_size", nullable = false)
    private Integer backPropagateSize;

    @Column(name = "max_episode", nullable = false)
    private Integer maxEpisode;
}
