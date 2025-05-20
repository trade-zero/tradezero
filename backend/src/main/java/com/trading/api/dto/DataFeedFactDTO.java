package com.trading.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for DataFeedFact entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataFeedFactDTO {
    
    private UUID dataFeedUuid;
    
    @NotBlank(message = "Name is required")
    private String name;
}
