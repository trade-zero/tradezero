package com.trading.api.model;

import com.trading.api.model.enums.OrderType;
import com.trading.api.model.enums.TradeActionType;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the order_dim table in the database.
 */
@Entity
@Table(name = "order_dim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDim {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_dim_uuid")
    private UUID orderDimUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false)
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction_type", nullable = false)
    private TradeDirectionType directionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private TradeActionType actionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type", nullable = false)
    private TradeAssetType assetType;

    @Column(name = "volume", nullable = false)
    private Double volume;
}
