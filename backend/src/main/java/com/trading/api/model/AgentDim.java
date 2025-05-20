package com.trading.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the agent_dim table in the database.
 */
@Entity
@Table(name = "agent_dim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentDim {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "agent_dim_uuid")
    private UUID agentDimUuid;

    @Column(name = "name", nullable = false, length = 125)
    private String name;
}
