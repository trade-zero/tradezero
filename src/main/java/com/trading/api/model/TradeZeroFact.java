package com.trading.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the trade_zero_fact table in the database.
 */
@Entity
@Table(name = "trade_zero_fact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeZeroFact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trade_zero_fact_uuid")
    private UUID tradeZeroFactUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_zero_dim_uuid", nullable = false)
    private TradeZeroDim tradeZeroDim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_dim_uuid", nullable = false)
    private AgentDim agentDim;

    @Column(name = "epoch")
    private Integer epoch;

    @Column(name = "trained")
    private Boolean trained = false;
}
