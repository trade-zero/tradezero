package com.trading.api.model;

import com.trading.api.model.enums.TradeTimeFrameType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing the time_frame_dim table in the database.
 */
@Entity
@Table(name = "time_frame_dim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeFrameDim {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "time_frame")
    private TradeTimeFrameType timeFrame;

    @Column(name = "description", nullable = false, length = 50)
    private String description;
}
