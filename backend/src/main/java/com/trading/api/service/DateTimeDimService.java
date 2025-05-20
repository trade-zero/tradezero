package com.trading.api.service;

import com.trading.api.dto.DateTimeDimDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.DateTimeDim;
import com.trading.api.repository.DateTimeDimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for DateTimeDim entity operations.
 */
@Service
@RequiredArgsConstructor
public class DateTimeDimService {

    private final DateTimeDimRepository dateTimeDimRepository;

    /**
     * Get all datetime records.
     *
     * @return list of all datetime records
     */
    public List<DateTimeDimDTO> findAll() {
        return dateTimeDimRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get datetime by ID.
     *
     * @param id the datetime ID
     * @return the datetime
     * @throws ResourceNotFoundException if datetime not found
     */
    public DateTimeDimDTO findById(Long id) {
        return dateTimeDimRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Datetime not found with id: " + id));
    }

    /**
     * Create a new datetime.
     *
     * @param dateTimeDimDTO the datetime to create
     * @return the created datetime
     */
    @Transactional
    public DateTimeDimDTO create(DateTimeDimDTO dateTimeDimDTO) {
        DateTimeDim dateTimeDim = convertToEntity(dateTimeDimDTO);
        DateTimeDim savedDateTimeDim = dateTimeDimRepository.save(dateTimeDim);
        return convertToDTO(savedDateTimeDim);
    }

    /**
     * Update an existing datetime.
     *
     * @param id the datetime ID
     * @param dateTimeDimDTO the datetime data to update
     * @return the updated datetime
     * @throws ResourceNotFoundException if datetime not found
     */
    @Transactional
    public DateTimeDimDTO update(Long id, DateTimeDimDTO dateTimeDimDTO) {
        return dateTimeDimRepository.findById(id)
                .map(existingDateTime -> {
                    // Update all fields from DTO to entity
                    existingDateTime.setDatetime(dateTimeDimDTO.getDatetime());
                    existingDateTime.setEpoch(dateTimeDimDTO.getEpoch());
                    existingDateTime.setDayOfWeek(dateTimeDimDTO.getDayOfWeek());
                    existingDateTime.setDayOfMonth(dateTimeDimDTO.getDayOfMonth());
                    existingDateTime.setDayOfYear(dateTimeDimDTO.getDayOfYear());
                    existingDateTime.setWeekOfMonth(dateTimeDimDTO.getWeekOfMonth());
                    existingDateTime.setWeekOfYear(dateTimeDimDTO.getWeekOfYear());
                    existingDateTime.setMonth(dateTimeDimDTO.getMonth());
                    existingDateTime.setQuarter(dateTimeDimDTO.getQuarter());
                    existingDateTime.setYear(dateTimeDimDTO.getYear());
                    existingDateTime.setStartOfWeek(dateTimeDimDTO.getStartOfWeek());
                    existingDateTime.setStartOfMonth(dateTimeDimDTO.getStartOfMonth());
                    existingDateTime.setIsWeekend(dateTimeDimDTO.getIsWeekend());
                    existingDateTime.setHour(dateTimeDimDTO.getHour());
                    existingDateTime.setMinute(dateTimeDimDTO.getMinute());

                    return convertToDTO(dateTimeDimRepository.save(existingDateTime));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Datetime not found with id: " + id));
    }

    /**
     * Delete a datetime by ID.
     *
     * @param id the datetime ID
     * @throws ResourceNotFoundException if datetime not found
     */
    @Transactional
    public void delete(Long id) {
        if (!dateTimeDimRepository.existsById(id)) {
            throw new ResourceNotFoundException("Datetime not found with id: " + id);
        }
        dateTimeDimRepository.deleteById(id);
    }

    /**
     * Convert entity to DTO.
     *
     * @param dateTimeDim the entity
     * @return the DTO
     */
    private DateTimeDimDTO convertToDTO(DateTimeDim dateTimeDim) {
        DateTimeDimDTO dto = new DateTimeDimDTO();
        dto.setDatetimeId(dateTimeDim.getDatetimeId());
        dto.setDatetime(dateTimeDim.getDatetime());
        dto.setEpoch(dateTimeDim.getEpoch());
        dto.setDayOfWeek(dateTimeDim.getDayOfWeek());
        dto.setDayOfMonth(dateTimeDim.getDayOfMonth());
        dto.setDayOfYear(dateTimeDim.getDayOfYear());
        dto.setWeekOfMonth(dateTimeDim.getWeekOfMonth());
        dto.setWeekOfYear(dateTimeDim.getWeekOfYear());
        dto.setMonth(dateTimeDim.getMonth());
        dto.setQuarter(dateTimeDim.getQuarter());
        dto.setYear(dateTimeDim.getYear());
        dto.setStartOfWeek(dateTimeDim.getStartOfWeek());
        dto.setStartOfMonth(dateTimeDim.getStartOfMonth());
        dto.setIsWeekend(dateTimeDim.getIsWeekend());
        dto.setHour(dateTimeDim.getHour());
        dto.setMinute(dateTimeDim.getMinute());
        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param dateTimeDimDTO the DTO
     * @return the entity
     */
    private DateTimeDim convertToEntity(DateTimeDimDTO dateTimeDimDTO) {
        DateTimeDim entity = new DateTimeDim();
        entity.setDatetimeId(dateTimeDimDTO.getDatetimeId());
        entity.setDatetime(dateTimeDimDTO.getDatetime());
        entity.setEpoch(dateTimeDimDTO.getEpoch());
        entity.setDayOfWeek(dateTimeDimDTO.getDayOfWeek());
        entity.setDayOfMonth(dateTimeDimDTO.getDayOfMonth());
        entity.setDayOfYear(dateTimeDimDTO.getDayOfYear());
        entity.setWeekOfMonth(dateTimeDimDTO.getWeekOfMonth());
        entity.setWeekOfYear(dateTimeDimDTO.getWeekOfYear());
        entity.setMonth(dateTimeDimDTO.getMonth());
        entity.setQuarter(dateTimeDimDTO.getQuarter());
        entity.setYear(dateTimeDimDTO.getYear());
        entity.setStartOfWeek(dateTimeDimDTO.getStartOfWeek());
        entity.setStartOfMonth(dateTimeDimDTO.getStartOfMonth());
        entity.setIsWeekend(dateTimeDimDTO.getIsWeekend());
        entity.setHour(dateTimeDimDTO.getHour());
        entity.setMinute(dateTimeDimDTO.getMinute());
        return entity;
    }
}
