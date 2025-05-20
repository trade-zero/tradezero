package com.trading.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the action_fact table in the database.
 */
@Entity
@Table(name = "action_fact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionFact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "action_fact_uuid")
    private UUID actionFactUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_management_uuid", nullable = false)
    private RiskManagementFact riskManagement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_dim_uuid", nullable = false)
    private ActionDim actionDim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "datetime_id", nullable = false)
    private DateTimeDim dateTime;
}
