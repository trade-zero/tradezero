package com.trading.api.service;

import com.trading.api.dto.TradeZeroFactDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.TradeZeroFact;
import com.trading.api.repository.TradeZeroFactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for TradeZeroFact entity operations.
 */
@Service
@RequiredArgsConstructor
public class TradeZeroFactService {

    private final TradeZeroFactRepository tradeZeroFactRepository;

    /**
     * Get all trade zero facts.
     *
     * @return list of all trade zero facts
     */
    public List<TradeZeroFactDTO> findAll() {
        return tradeZeroFactRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get trade zero fact by UUID.
     *
     * @param uuid the trade zero fact UUID
     * @return the trade zero fact
     * @throws ResourceNotFoundException if trade zero fact not found
     */
    public TradeZeroFactDTO findById(UUID uuid) {
        return tradeZeroFactRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Trade zero fact not found with id: " + uuid));
    }

    /**
     * Get trade zero facts by trade zero dimension UUID.
     *
     * @param tradeZeroDimUuid the trade zero dimension UUID
     * @return list of trade zero facts
     */
    public List<TradeZeroFactDTO> findByTradeZeroDimUuid(UUID tradeZeroDimUuid) {
        return tradeZeroFactRepository.findByTradeZeroDim_TradeZeroDimUuid(tradeZeroDimUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get trade zero facts by agent dimension UUID.
     *
     * @param agentDimUuid the agent dimension UUID
     * @return list of trade zero facts
     */
    public List<TradeZeroFactDTO> findByAgentDimUuid(UUID agentDimUuid) {
        return tradeZeroFactRepository.findByAgentDim_AgentDimUuid(agentDimUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get trade zero facts by epoch.
     *
     * @param epoch the epoch
     * @return list of trade zero facts
     */
    public List<TradeZeroFactDTO> findByEpoch(Integer epoch) {
        return tradeZeroFactRepository.findByEpoch(epoch).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get trade zero facts by trained status.
     *
     * @param trained the trained status
     * @return list of trade zero facts
     */
    public List<TradeZeroFactDTO> findByTrained(Boolean trained) {
        return tradeZeroFactRepository.findByTrained(trained).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new trade zero fact.
     *
     * @param tradeZeroFactDTO the trade zero fact to create
     * @return the created trade zero fact
     */
    @Transactional
    public TradeZeroFactDTO create(TradeZeroFactDTO tradeZeroFactDTO) {
        TradeZeroFact tradeZeroFact = convertToEntity(tradeZeroFactDTO);
        tradeZeroFact.setTradeZeroFactUuid(null); // Ensure UUID is generated
        TradeZeroFact savedTradeZeroFact = tradeZeroFactRepository.save(tradeZeroFact);
        return convertToDTO(savedTradeZeroFact);
    }

    /**
     * Update an existing trade zero fact.
     *
     * @param uuid the trade zero fact UUID
     * @param tradeZeroFactDTO the trade zero fact data to update
     * @return the updated trade zero fact
     * @throws ResourceNotFoundException if trade zero fact not found
     */
    @Transactional
    public TradeZeroFactDTO update(UUID uuid, TradeZeroFactDTO tradeZeroFactDTO) {
        return tradeZeroFactRepository.findById(uuid)
                .map(existingTradeZeroFact -> {
                    // In a real implementation, you would need to fetch related entities
                    // and set them properly instead of just UUIDs
                    return convertToDTO(tradeZeroFactRepository.save(existingTradeZeroFact));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Trade zero fact not found with id: " + uuid));
    }

    /**
     * Delete a trade zero fact by UUID.
     *
     * @param uuid the trade zero fact UUID
     * @throws ResourceNotFoundException if trade zero fact not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!tradeZeroFactRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Trade zero fact not found with id: " + uuid);
        }
        tradeZeroFactRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param tradeZeroFact the entity
     * @return the DTO
     */
    private TradeZeroFactDTO convertToDTO(TradeZeroFact tradeZeroFact) {
        TradeZeroFactDTO dto = new TradeZeroFactDTO();
        dto.setTradeZeroFactUuid(tradeZeroFact.getTradeZeroFactUuid());

        // Handle relationships properly
        if (tradeZeroFact.getTradeZeroDim() != null) {
            dto.setTradeZeroDimUuid(tradeZeroFact.getTradeZeroDim().getTradeZeroDimUuid());
        }

        if (tradeZeroFact.getAgentDim() != null) {
            dto.setAgentDimUuid(tradeZeroFact.getAgentDim().getAgentDimUuid());
        }

        dto.setEpoch(tradeZeroFact.getEpoch());
        dto.setTrained(tradeZeroFact.getTrained());
        return dto;
    }

    /**
     * Convert DTO to entity.
     * Note: In a real implementation, you would need to fetch related entities
     * from their respective repositories.
     *
     * @param tradeZeroFactDTO the DTO
     * @return the entity
     */
    private TradeZeroFact convertToEntity(TradeZeroFactDTO tradeZeroFactDTO) {
        TradeZeroFact entity = new TradeZeroFact();
        entity.setTradeZeroFactUuid(tradeZeroFactDTO.getTradeZeroFactUuid());

        // In a real implementation, you would fetch related entities
        // and set them properly instead of just setting UUIDs

        entity.setEpoch(tradeZeroFactDTO.getEpoch());
        entity.setTrained(tradeZeroFactDTO.getTrained());
        return entity;
    }
}
