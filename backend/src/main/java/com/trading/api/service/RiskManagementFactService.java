package com.trading.api.service;

import com.trading.api.dto.RiskManagementFactDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.RiskManagementFact;
import com.trading.api.model.TradeZeroFact;
import com.trading.api.repository.RiskManagementFactRepository;
import com.trading.api.repository.TradeZeroFactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for RiskManagementFact entity operations.
 */
@Service
@RequiredArgsConstructor
public class RiskManagementFactService {

    private final RiskManagementFactRepository riskManagementFactRepository;
    private final TradeZeroFactRepository tradeZeroFactRepository;

    /**
     * Get all risk management facts.
     *
     * @return list of all risk management facts
     */
    public List<RiskManagementFactDTO> findAll() {
        return riskManagementFactRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get risk management fact by UUID.
     *
     * @param uuid the risk management fact UUID
     * @return the risk management fact
     * @throws ResourceNotFoundException if risk management fact not found
     */
    public RiskManagementFactDTO findById(UUID uuid) {
        return riskManagementFactRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Risk management fact not found with id: " + uuid));
    }

    /**
     * Get risk management facts by trade zero fact UUID.
     *
     * @param tradeZeroFactUuid the trade zero fact UUID
     * @return list of risk management facts
     */
    public List<RiskManagementFactDTO> findByTradeZeroFactUuid(UUID tradeZeroFactUuid) {
        return riskManagementFactRepository.findByTradeZeroFact_TradeZeroFactUuid(tradeZeroFactUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get risk management facts by actions count.
     *
     * @param actions the actions count
     * @return list of risk management facts
     */
    public List<RiskManagementFactDTO> findByActions(Integer actions) {
        return riskManagementFactRepository.findByActions(actions).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new risk management fact.
     *
     * @param riskManagementFactDTO the risk management fact to create
     * @return the created risk management fact
     */
    @Transactional
    public RiskManagementFactDTO create(RiskManagementFactDTO riskManagementFactDTO) {
        RiskManagementFact riskManagementFact = convertToEntity(riskManagementFactDTO);
        riskManagementFact.setRiskManagementUuid(null); // Ensure UUID is generated
        RiskManagementFact savedRiskManagementFact = riskManagementFactRepository.save(riskManagementFact);
        return convertToDTO(savedRiskManagementFact);
    }

    /**
     * Update an existing risk management fact.
     *
     * @param uuid the risk management fact UUID
     * @param riskManagementFactDTO the risk management fact data to update
     * @return the updated risk management fact
     * @throws ResourceNotFoundException if risk management fact not found
     */
    @Transactional
    public RiskManagementFactDTO update(UUID uuid, RiskManagementFactDTO riskManagementFactDTO) {
        return riskManagementFactRepository.findById(uuid)
                .map(existingRiskManagementFact -> {
                    // Find the TradeZeroFact entity
                    TradeZeroFact tradeZeroFact = tradeZeroFactRepository.findById(riskManagementFactDTO.getTradeZeroFactUuid())
                            .orElseThrow(() -> new ResourceNotFoundException("Trade zero fact not found with id: " +
                                    riskManagementFactDTO.getTradeZeroFactUuid()));

                    existingRiskManagementFact.setTradeZeroFact(tradeZeroFact);
                    existingRiskManagementFact.setActions(riskManagementFactDTO.getActions());
                    existingRiskManagementFact.setValidInputs(riskManagementFactDTO.getValidInputs());

                    return convertToDTO(riskManagementFactRepository.save(existingRiskManagementFact));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Risk management fact not found with id: " + uuid));
    }

    /**
     * Delete a risk management fact by UUID.
     *
     * @param uuid the risk management fact UUID
     * @throws ResourceNotFoundException if risk management fact not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!riskManagementFactRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Risk management fact not found with id: " + uuid);
        }
        riskManagementFactRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param riskManagementFact the entity
     * @return the DTO
     */
    private RiskManagementFactDTO convertToDTO(RiskManagementFact riskManagementFact) {
        RiskManagementFactDTO dto = new RiskManagementFactDTO();
        dto.setRiskManagementUuid(riskManagementFact.getRiskManagementUuid());

        // Extract UUID from TradeZeroFact relationship
        if (riskManagementFact.getTradeZeroFact() != null) {
            dto.setTradeZeroFactUuid(riskManagementFact.getTradeZeroFact().getTradeZeroFactUuid());
        }

        dto.setActions(riskManagementFact.getActions());
        dto.setValidInputs(riskManagementFact.getValidInputs());
        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param riskManagementFactDTO the DTO
     * @return the entity
     */
    private RiskManagementFact convertToEntity(RiskManagementFactDTO riskManagementFactDTO) {
        RiskManagementFact entity = new RiskManagementFact();
        entity.setRiskManagementUuid(riskManagementFactDTO.getRiskManagementUuid());

        // Set TradeZeroFact relationship if UUID is provided
        if (riskManagementFactDTO.getTradeZeroFactUuid() != null) {
            TradeZeroFact tradeZeroFact = tradeZeroFactRepository.findById(riskManagementFactDTO.getTradeZeroFactUuid())
                    .orElseThrow(() -> new ResourceNotFoundException("Trade zero fact not found with id: " +
                            riskManagementFactDTO.getTradeZeroFactUuid()));
            entity.setTradeZeroFact(tradeZeroFact);
        }

        entity.setActions(riskManagementFactDTO.getActions());
        entity.setValidInputs(riskManagementFactDTO.getValidInputs());
        return entity;
    }
}
