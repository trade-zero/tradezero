package com.trading.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * Entity representing the datetime_dim table in the database.
 */
@Entity
@Table(name = "datetime_dim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateTimeDim {

    @Id
    @Column(name = "datetime_id")
    private Long datetimeId;

    @Column(name = "datetime", nullable = false)
    private LocalDateTime datetime;

    @Column(name = "epoch", nullable = false)
    private Long epoch;

    @Column(name = "day_of_week", nullable = false)
    private Short dayOfWeek;

    @Column(name = "day_of_month", nullable = false)
    private Short dayOfMonth;

    @Column(name = "day_of_year", nullable = false)
    private Short dayOfYear;

    @Column(name = "week_of_month", nullable = false)
    private Short weekOfMonth;

    @Column(name = "week_of_year", nullable = false)
    private Short weekOfYear;

    @Column(name = "month_", nullable = false)
    private Short month;

    @Column(name = "quarter_", nullable = false)
    private Short quarter;

    @Column(name = "year", nullable = false)
    private Short year;

    @Column(name = "start_of_week", nullable = false)
    private LocalDate startOfWeek;

    @Column(name = "start_of_month", nullable = false)
    private LocalDate startOfMonth;

    @Column(name = "is_weekend", nullable = false)
    private Boolean isWeekend;

    @Column(name = "hour", nullable = false)
    private Short hour;

    @Column(name = "minute", nullable = false)
    private Short minute;
}
