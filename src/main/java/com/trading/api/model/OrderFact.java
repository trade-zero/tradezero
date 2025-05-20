package com.trading.api.model;

import com.trading.api.model.enums.OrderStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the order_fact table in the database.
 */
@Entity
@Table(name = "order_fact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_fact_uuid")
    private UUID orderFactUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_dim_uuid", nullable = false)
    private OrderDim orderDim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_venue_dim_uuid", nullable = false)
    private OrderVenueDim orderVenueDim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "datetime_id", nullable = false)
    private DateTimeDim dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_uuid", nullable = false)
    private PortfolioFact portfolio;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatusType orderStatus;

    @Column(name = "executed_price")
    private Double executedPrice;

    @Column(name = "limit_price")
    private Double limitPrice;

    @Column(name = "stop_price")
    private Double stopPrice;

    @Column(name = "fees")
    private Double fees;

    @Column(name = "slippage")
    private Double slippage;

    @Column(name = "latency_ms")
    private Integer latencyMs;
}
