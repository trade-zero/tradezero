package com.trading.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the order_venue_dim table in the database.
 */
@Entity
@Table(name = "order_venue_dim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVenueDim {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_venue_dim_uuid")
    private UUID orderVenueDimUuid;

    @Column(name = "exchange", nullable = false, length = 50)
    private String exchange;

    @Column(name = "broker", nullable = false, length = 50)
    private String broker;

    @Column(name = "platform", nullable = false, length = 50)
    private String platform;
}
