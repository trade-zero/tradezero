package com.trading.api.model;

import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the position_dim table in the database.
 */
@Entity
@Table(name = "position_dim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDim {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "position_dim_uuid")
    private UUID positionDimUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type", nullable = false)
    private TradeAssetType assetType;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction_type", nullable = false)
    private TradeDirectionType directionType;

    @Column(name = "value")
    private Double value;
}
