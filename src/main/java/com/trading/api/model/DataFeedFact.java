package com.trading.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing the data_feed_fact table in the database.
 */
@Entity
@Table(name = "data_feed_fact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataFeedFact {

    @Id
    @Column(name = "data_feed_uuid")
    private UUID dataFeedUuid;

    @Column(name = "name", nullable = false)
    private String name;
}
