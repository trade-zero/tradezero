package com.trading.api.service;

import com.trading.api.dto.TimeFrameDimDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.TimeFrameDim;
import com.trading.api.model.enums.TradeTimeFrameType;
import com.trading.api.repository.TimeFrameDimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for TimeFrameDim entity operations.
 */
@Service
@RequiredArgsConstructor
public class TimeFrameDimService {

    private final TimeFrameDimRepository timeFrameDimRepository;

    /**
     * Get all time frames.
     *
     * @return list of all time frames
     */
    public List<TimeFrameDimDTO> findAll() {
        return timeFrameDimRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get time frame by time frame type.
     *
     * @param timeFrame the time frame type
     * @return the time frame
     * @throws ResourceNotFoundException if time frame not found
     */
    public TimeFrameDimDTO findById(TradeTimeFrameType timeFrame) {
        return timeFrameDimRepository.findById(timeFrame)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Time frame not found with id: " + timeFrame));
    }

    /**
     * Create a new time frame.
     *
     * @param timeFrameDimDTO the time frame to create
     * @return the created time frame
     */
    @Transactional
    public TimeFrameDimDTO create(TimeFrameDimDTO timeFrameDimDTO) {
        TimeFrameDim timeFrameDim = convertToEntity(timeFrameDimDTO);
        TimeFrameDim savedTimeFrameDim = timeFrameDimRepository.save(timeFrameDim);
        return convertToDTO(savedTimeFrameDim);
    }

    /**
     * Update an existing time frame.
     *
     * @param timeFrame the time frame type
     * @param timeFrameDimDTO the time frame data to update
     * @return the updated time frame
     * @throws ResourceNotFoundException if time frame not found
     */
    @Transactional
    public TimeFrameDimDTO update(TradeTimeFrameType timeFrame, TimeFrameDimDTO timeFrameDimDTO) {
        return timeFrameDimRepository.findById(timeFrame)
                .map(existingTimeFrame -> {
                    existingTimeFrame.setDescription(timeFrameDimDTO.getDescription());
                    return convertToDTO(timeFrameDimRepository.save(existingTimeFrame));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Time frame not found with id: " + timeFrame));
    }

    /**
     * Delete a time frame by time frame type.
     *
     * @param timeFrame the time frame type
     * @throws ResourceNotFoundException if time frame not found
     */
    @Transactional
    public void delete(TradeTimeFrameType timeFrame) {
        if (!timeFrameDimRepository.existsById(timeFrame)) {
            throw new ResourceNotFoundException("Time frame not found with id: " + timeFrame);
        }
        timeFrameDimRepository.deleteById(timeFrame);
    }

    /**
     * Convert entity to DTO.
     *
     * @param timeFrameDim the entity
     * @return the DTO
     */
    private TimeFrameDimDTO convertToDTO(TimeFrameDim timeFrameDim) {
        TimeFrameDimDTO dto = new TimeFrameDimDTO();
        dto.setTimeFrame(timeFrameDim.getTimeFrame());
        dto.setDescription(timeFrameDim.getDescription());
        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param timeFrameDimDTO the DTO
     * @return the entity
     */
    private TimeFrameDim convertToEntity(TimeFrameDimDTO timeFrameDimDTO) {
        TimeFrameDim entity = new TimeFrameDim();
        entity.setTimeFrame(timeFrameDimDTO.getTimeFrame());
        entity.setDescription(timeFrameDimDTO.getDescription());
        return entity;
    }
}
