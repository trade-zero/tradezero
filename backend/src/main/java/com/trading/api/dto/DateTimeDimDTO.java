package com.trading.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * DTO for DateTimeDim entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateTimeDimDTO {

    @NotNull(message = "Datetime ID is required")
    private Long datetimeId;

    @NotNull(message = "Datetime is required")
    private LocalDateTime datetime;

    @NotNull(message = "Epoch is required")
    private Long epoch;

    @NotNull(message = "Day of week is required")
    private Short dayOfWeek;

    @NotNull(message = "Day of month is required")
    private Short dayOfMonth;

    @NotNull(message = "Day of year is required")
    private Short dayOfYear;

    @NotNull(message = "Week of month is required")
    private Short weekOfMonth;

    @NotNull(message = "Week of year is required")
    private Short weekOfYear;

    @NotNull(message = "Month is required")
    private Short month;

    @NotNull(message = "Quarter is required")
    private Short quarter;

    @NotNull(message = "Year is required")
    private Short year;

    @NotNull(message = "Start of week is required")
    private LocalDate startOfWeek;

    @NotNull(message = "Start of month is required")
    private LocalDate startOfMonth;

    @NotNull(message = "Is weekend flag is required")
    private Boolean isWeekend;

    @NotNull(message = "Hour is required")
    private Short hour;

    @NotNull(message = "Minute is required")
    private Short minute;
}
