package com.trading.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the position_fact table in the database.
 */
@Entity
@Table(name = "position_fact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionFact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "position_uuid")
    private UUID positionUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_uuid", nullable = false)
    private PortfolioFact portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_dim_uuid", nullable = false)
    private PositionDim positionDim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "datetime_id", nullable = false)
    private DateTimeDim dateTime;

    @Column(name = "entry_price", nullable = false)
    private Double entryPrice;
}
