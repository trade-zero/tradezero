package com.trading.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the portfolio_fact table in the database.
 */
@Entity
@Table(name = "portfolio_fact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioFact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "portfolio_uuid")
    private UUID portfolioUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_zero_fact_uuid", nullable = false)
    private TradeZeroFact tradeZeroFact;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description")
    private String description;
}
