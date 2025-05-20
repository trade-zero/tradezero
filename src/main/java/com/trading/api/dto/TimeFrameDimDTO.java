package com.trading.api.dto;

import com.trading.api.model.enums.TradeTimeFrameType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for TimeFrameDim entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeFrameDimDTO {
    
    @NotNull(message = "Time frame is required")
    private TradeTimeFrameType timeFrame;
    
    @NotBlank(message = "Description is required")
    private String description;
}
