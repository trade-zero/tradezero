package com.trading.api.model;

import com.trading.api.model.enums.TradeActionType;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the action_dim table in the database.
 */
@Entity
@Table(name = "action_dim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionDim {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "action_dim_uuid")
    private UUID actionDimUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type", nullable = false)
    private TradeAssetType assetType;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction_type", nullable = false)
    private TradeDirectionType directionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private TradeActionType actionType;

    @Column(name = "volume", nullable = false)
    private Double volume;
}
