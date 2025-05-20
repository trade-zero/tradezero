package com.trading.api.service;

import com.trading.api.dto.RiskMetricsFactDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.DateTimeDim;
import com.trading.api.model.RiskManagementFact;
import com.trading.api.model.RiskMetricsFact;
import com.trading.api.repository.DateTimeDimRepository;
import com.trading.api.repository.RiskManagementFactRepository;
import com.trading.api.repository.RiskMetricsFactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for RiskMetricsFact entity operations.
 */
@Service
@RequiredArgsConstructor
public class RiskMetricsFactService {

    private final RiskMetricsFactRepository riskMetricsFactRepository;
    private final RiskManagementFactRepository riskManagementFactRepository;
    private final DateTimeDimRepository dateTimeDimRepository;

    /**
     * Get all risk metrics facts.
     *
     * @return list of all risk metrics facts
     */
    public List<RiskMetricsFactDTO> findAll() {
        return riskMetricsFactRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get risk metrics fact by UUID.
     *
     * @param uuid the risk metrics fact UUID
     * @return the risk metrics fact
     * @throws ResourceNotFoundException if risk metrics fact not found
     */
    public RiskMetricsFactDTO findById(UUID uuid) {
        return riskMetricsFactRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Risk metrics fact not found with id: " + uuid));
    }

    /**
     * Get risk metrics facts by risk management UUID.
     *
     * @param riskManagementUuid the risk management UUID
     * @return list of risk metrics facts
     */
    public List<RiskMetricsFactDTO> findByRiskManagementUuid(UUID riskManagementUuid) {
        return riskMetricsFactRepository.findByRiskManagement_RiskManagementUuid(riskManagementUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get risk metrics facts by datetime ID.
     *
     * @param datetimeId the datetime ID
     * @return list of risk metrics facts
     */
    public List<RiskMetricsFactDTO> findByDatetimeId(Long datetimeId) {
        return riskMetricsFactRepository.findByDateTime_DatetimeId(datetimeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get risk metrics fact by risk management UUID and datetime ID.
     *
     * @param riskManagementUuid the risk management UUID
     * @param datetimeId the datetime ID
     * @return the risk metrics fact
     * @throws ResourceNotFoundException if risk metrics fact not found
     */
    public RiskMetricsFactDTO findByRiskManagementUuidAndDatetimeId(UUID riskManagementUuid, Long datetimeId) {
        return riskMetricsFactRepository.findByRiskManagement_RiskManagementUuidAndDateTime_DatetimeId(riskManagementUuid, datetimeId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Risk metrics fact not found with risk management id: " + riskManagementUuid +
                                " and datetime id: " + datetimeId));
    }

    /**
     * Get risk metrics facts by margin used greater than the specified value.
     *
     * @param marginUsed the minimum margin used
     * @return list of risk metrics facts
     */
    public List<RiskMetricsFactDTO> findByMarginUsedGreaterThan(Double marginUsed) {
        return riskMetricsFactRepository.findByMarginUsedGreaterThan(marginUsed).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get risk metrics facts by max drawdown less than the specified value.
     *
     * @param maxDrawdown the maximum drawdown
     * @return list of risk metrics facts
     */
    public List<RiskMetricsFactDTO> findByMaxDrawdownLessThan(Double maxDrawdown) {
        return riskMetricsFactRepository.findByMaxDrawdownLessThan(maxDrawdown).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get risk metrics facts by sharpe ratio greater than the specified value.
     *
     * @param sharpeRatio the minimum sharpe ratio
     * @return list of risk metrics facts
     */
    public List<RiskMetricsFactDTO> findBySharpeRatioGreaterThan(Double sharpeRatio) {
        return riskMetricsFactRepository.findBySharpeRatioGreaterThan(sharpeRatio).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new risk metrics fact.
     *
     * @param riskMetricsFactDTO the risk metrics fact to create
     * @return the created risk metrics fact
     */
    @Transactional
    public RiskMetricsFactDTO create(RiskMetricsFactDTO riskMetricsFactDTO) {
        RiskMetricsFact riskMetricsFact = convertToEntity(riskMetricsFactDTO);
        riskMetricsFact.setRiskMetricsUuid(null); // Ensure UUID is generated
        RiskMetricsFact savedRiskMetricsFact = riskMetricsFactRepository.save(riskMetricsFact);
        return convertToDTO(savedRiskMetricsFact);
    }

    /**
     * Update an existing risk metrics fact.
     *
     * @param uuid the risk metrics fact UUID
     * @param riskMetricsFactDTO the risk metrics fact data to update
     * @return the updated risk metrics fact
     * @throws ResourceNotFoundException if risk metrics fact not found
     */
    @Transactional
    public RiskMetricsFactDTO update(UUID uuid, RiskMetricsFactDTO riskMetricsFactDTO) {
        return riskMetricsFactRepository.findById(uuid)
                .map(existingRiskMetricsFact -> {
                    // Find related entities
                    RiskManagementFact riskManagement = riskManagementFactRepository.findById(riskMetricsFactDTO.getRiskManagementUuid())
                            .orElseThrow(() -> new ResourceNotFoundException("Risk management fact not found with id: " +
                                    riskMetricsFactDTO.getRiskManagementUuid()));

                    DateTimeDim dateTime = dateTimeDimRepository.findById(riskMetricsFactDTO.getDatetimeId())
                            .orElseThrow(() -> new ResourceNotFoundException("DateTime dimension not found with id: " +
                                    riskMetricsFactDTO.getDatetimeId()));

                    // Update entity with related entities and values
                    existingRiskMetricsFact.setRiskManagement(riskManagement);
                    existingRiskMetricsFact.setDateTime(dateTime);
                    existingRiskMetricsFact.setMarginUsed(riskMetricsFactDTO.getMarginUsed());
                    existingRiskMetricsFact.setMaxDrawdown(riskMetricsFactDTO.getMaxDrawdown());
                    existingRiskMetricsFact.setSharpeRatio(riskMetricsFactDTO.getSharpeRatio());

                    return convertToDTO(riskMetricsFactRepository.save(existingRiskMetricsFact));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Risk metrics fact not found with id: " + uuid));
    }

    /**
     * Delete a risk metrics fact by UUID.
     *
     * @param uuid the risk metrics fact UUID
     * @throws ResourceNotFoundException if risk metrics fact not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!riskMetricsFactRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Risk metrics fact not found with id: " + uuid);
        }
        riskMetricsFactRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param riskMetricsFact the entity
     * @return the DTO
     */
    private RiskMetricsFactDTO convertToDTO(RiskMetricsFact riskMetricsFact) {
        RiskMetricsFactDTO dto = new RiskMetricsFactDTO();
        dto.setRiskMetricsUuid(riskMetricsFact.getRiskMetricsUuid());

        // Extract UUIDs from related entities
        if (riskMetricsFact.getRiskManagement() != null) {
            dto.setRiskManagementUuid(riskMetricsFact.getRiskManagement().getRiskManagementUuid());
        }

        if (riskMetricsFact.getDateTime() != null) {
            dto.setDatetimeId(riskMetricsFact.getDateTime().getDatetimeId());
        }

        dto.setMarginUsed(riskMetricsFact.getMarginUsed());
        dto.setMaxDrawdown(riskMetricsFact.getMaxDrawdown());
        dto.setSharpeRatio(riskMetricsFact.getSharpeRatio());

        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param riskMetricsFactDTO the DTO
     * @return the entity
     */
    private RiskMetricsFact convertToEntity(RiskMetricsFactDTO riskMetricsFactDTO) {
        RiskMetricsFact entity = new RiskMetricsFact();
        entity.setRiskMetricsUuid(riskMetricsFactDTO.getRiskMetricsUuid());

        // Set related entities if UUIDs are provided
        if (riskMetricsFactDTO.getRiskManagementUuid() != null) {
            RiskManagementFact riskManagement = riskManagementFactRepository.findById(riskMetricsFactDTO.getRiskManagementUuid())
                    .orElseThrow(() -> new ResourceNotFoundException("Risk management fact not found with id: " +
                            riskMetricsFactDTO.getRiskManagementUuid()));
            entity.setRiskManagement(riskManagement);
        }

        if (riskMetricsFactDTO.getDatetimeId() != null) {
            DateTimeDim dateTime = dateTimeDimRepository.findById(riskMetricsFactDTO.getDatetimeId())
                    .orElseThrow(() -> new ResourceNotFoundException("DateTime dimension not found with id: " +
                            riskMetricsFactDTO.getDatetimeId()));
            entity.setDateTime(dateTime);
        }

        entity.setMarginUsed(riskMetricsFactDTO.getMarginUsed());
        entity.setMaxDrawdown(riskMetricsFactDTO.getMaxDrawdown());
        entity.setSharpeRatio(riskMetricsFactDTO.getSharpeRatio());

        return entity;
    }
}
