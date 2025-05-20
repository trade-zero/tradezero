package com.trading.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the balance_fact table in the database.
 */
@Entity
@Table(name = "balance_fact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceFact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "balance_uuid")
    private UUID balanceUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_uuid", nullable = false)
    private PortfolioFact portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "datetime_id", nullable = false)
    private DateTimeDim dateTime;

    @Column(name = "initial", nullable = false)
    private Double initial;

    @Column(name = "current", nullable = false)
    private Double current;

    @Column(name = "max", nullable = false)
    private Double max;

    @Column(name = "min", nullable = false)
    private Double min;
}
