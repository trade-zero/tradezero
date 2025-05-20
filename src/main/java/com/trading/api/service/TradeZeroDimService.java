package com.trading.api.service;

import com.trading.api.dto.TradeZeroDimDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.TradeZeroDim;
import com.trading.api.model.enums.TradeTimeFrameType;
import com.trading.api.repository.TradeZeroDimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for TradeZeroDim entity operations.
 */
@Service
@RequiredArgsConstructor
public class TradeZeroDimService {

    private final TradeZeroDimRepository tradeZeroDimRepository;

    /**
     * Get all trade zero dimensions.
     *
     * @return list of all trade zero dimensions
     */
    public List<TradeZeroDimDTO> findAll() {
        return tradeZeroDimRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get trade zero dimension by UUID.
     *
     * @param uuid the trade zero dimension UUID
     * @return the trade zero dimension
     * @throws ResourceNotFoundException if trade zero dimension not found
     */
    public TradeZeroDimDTO findById(UUID uuid) {
        return tradeZeroDimRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Trade zero dimension not found with id: " + uuid));
    }

    /**
     * Get trade zero dimensions by time frame.
     *
     * @param timeFrame the time frame
     * @return list of trade zero dimensions
     */
    public List<TradeZeroDimDTO> findByTimeFrame(TradeTimeFrameType timeFrame) {
        return tradeZeroDimRepository.findByTradeTimeFrame(timeFrame).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new trade zero dimension.
     *
     * @param tradeZeroDimDTO the trade zero dimension to create
     * @return the created trade zero dimension
     */
    @Transactional
    public TradeZeroDimDTO create(TradeZeroDimDTO tradeZeroDimDTO) {
        TradeZeroDim tradeZeroDim = convertToEntity(tradeZeroDimDTO);
        tradeZeroDim.setTradeZeroDimUuid(null); // Ensure UUID is generated
        TradeZeroDim savedTradeZeroDim = tradeZeroDimRepository.save(tradeZeroDim);
        return convertToDTO(savedTradeZeroDim);
    }

    /**
     * Update an existing trade zero dimension.
     *
     * @param uuid the trade zero dimension UUID
     * @param tradeZeroDimDTO the trade zero dimension data to update
     * @return the updated trade zero dimension
     * @throws ResourceNotFoundException if trade zero dimension not found
     */
    @Transactional
    public TradeZeroDimDTO update(UUID uuid, TradeZeroDimDTO tradeZeroDimDTO) {
        return tradeZeroDimRepository.findById(uuid)
                .map(existingTradeZeroDim -> {
                    existingTradeZeroDim.setTradeAsset(tradeZeroDimDTO.getTradeAsset());
                    existingTradeZeroDim.setTradeTimeFrame(tradeZeroDimDTO.getTradeTimeFrame());
                    existingTradeZeroDim.setBalanceInitial(tradeZeroDimDTO.getBalanceInitial());
                    existingTradeZeroDim.setDrawdown(tradeZeroDimDTO.getDrawdown());
                    existingTradeZeroDim.setMaxVolume(tradeZeroDimDTO.getMaxVolume());
                    existingTradeZeroDim.setMaxHold(tradeZeroDimDTO.getMaxHold());
                    existingTradeZeroDim.setLookBack(tradeZeroDimDTO.getLookBack());
                    existingTradeZeroDim.setLookForward(tradeZeroDimDTO.getLookForward());
                    existingTradeZeroDim.setBackPropagateSize(tradeZeroDimDTO.getBackPropagateSize());
                    existingTradeZeroDim.setMaxEpisode(tradeZeroDimDTO.getMaxEpisode());
                    return convertToDTO(tradeZeroDimRepository.save(existingTradeZeroDim));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Trade zero dimension not found with id: " + uuid));
    }

    /**
     * Delete a trade zero dimension by UUID.
     *
     * @param uuid the trade zero dimension UUID
     * @throws ResourceNotFoundException if trade zero dimension not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!tradeZeroDimRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Trade zero dimension not found with id: " + uuid);
        }
        tradeZeroDimRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param tradeZeroDim the entity
     * @return the DTO
     */
    private TradeZeroDimDTO convertToDTO(TradeZeroDim tradeZeroDim) {
        TradeZeroDimDTO dto = new TradeZeroDimDTO();
        dto.setTradeZeroDimUuid(tradeZeroDim.getTradeZeroDimUuid());
        dto.setTradeAsset(tradeZeroDim.getTradeAsset());
        dto.setTradeTimeFrame(tradeZeroDim.getTradeTimeFrame());
        dto.setBalanceInitial(tradeZeroDim.getBalanceInitial());
        dto.setDrawdown(tradeZeroDim.getDrawdown());
        dto.setMaxVolume(tradeZeroDim.getMaxVolume());
        dto.setMaxHold(tradeZeroDim.getMaxHold());
        dto.setLookBack(tradeZeroDim.getLookBack());
        dto.setLookForward(tradeZeroDim.getLookForward());
        dto.setBackPropagateSize(tradeZeroDim.getBackPropagateSize());
        dto.setMaxEpisode(tradeZeroDim.getMaxEpisode());
        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param tradeZeroDimDTO the DTO
     * @return the entity
     */
    private TradeZeroDim convertToEntity(TradeZeroDimDTO tradeZeroDimDTO) {
        TradeZeroDim entity = new TradeZeroDim();
        entity.setTradeZeroDimUuid(tradeZeroDimDTO.getTradeZeroDimUuid());
        entity.setTradeAsset(tradeZeroDimDTO.getTradeAsset());
        entity.setTradeTimeFrame(tradeZeroDimDTO.getTradeTimeFrame());
        entity.setBalanceInitial(tradeZeroDimDTO.getBalanceInitial());
        entity.setDrawdown(tradeZeroDimDTO.getDrawdown());
        entity.setMaxVolume(tradeZeroDimDTO.getMaxVolume());
        entity.setMaxHold(tradeZeroDimDTO.getMaxHold());
        entity.setLookBack(tradeZeroDimDTO.getLookBack());
        entity.setLookForward(tradeZeroDimDTO.getLookForward());
        entity.setBackPropagateSize(tradeZeroDimDTO.getBackPropagateSize());
        entity.setMaxEpisode(tradeZeroDimDTO.getMaxEpisode());
        return entity;
    }
}
