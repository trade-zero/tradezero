package com.trading.api.model;

import com.trading.api.model.enums.TradeActionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.UUID;

/**
 * Entity representing the risk_management_fact table in the database.
 */
@Entity
@Table(name = "risk_management_fact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskManagementFact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "risk_management_uuid")
    private UUID riskManagementUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_zero_fact_uuid", nullable = false)
    private TradeZeroFact tradeZeroFact;

    @Column(name = "actions", nullable = false)
    private Integer actions;

    @Column(name = "valid_inputs", nullable = false, columnDefinition = "trade_action_type[]")
    private TradeActionType[] validInputs;
}
