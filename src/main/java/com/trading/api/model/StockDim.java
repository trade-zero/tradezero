package com.trading.api.model;

import com.trading.api.model.enums.TradeAssetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the stock_dim table in the database.
 */
@Entity
@Table(name = "stock_dim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDim {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stock_uuid")
    private UUID stockUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type", nullable = false, unique = true)
    private TradeAssetType assetType;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "tick_size", nullable = false)
    private Double tickSize;

    @Column(name = "tick_value", nullable = false)
    private Double tickValue;

    @Column(name = "volume_size", nullable = false)
    private Double volumeSize;
}
