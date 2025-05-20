package com.trading.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the risk_metrics_fact table in the database.
 */
@Entity
@Table(name = "risk_metrics_fact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskMetricsFact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "risk_metrics_uuid")
    private UUID riskMetricsUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_management_uuid", nullable = false)
    private RiskManagementFact riskManagement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "datetime_id", nullable = false)
    private DateTimeDim dateTime;

    @Column(name = "margin_used", nullable = false)
    private Double marginUsed;

    @Column(name = "max_drawdown", nullable = false)
    private Double maxDrawdown;

    @Column(name = "sharpe_ratio", nullable = false)
    private Double sharpeRatio;
}
