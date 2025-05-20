package com.trading.api.service;

import com.trading.api.dto.CandlestickFactDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.CandlestickFact;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeTimeFrameType;
import com.trading.api.repository.CandlestickFactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for CandlestickFact entity operations.
 */
@Service
@RequiredArgsConstructor
public class CandlestickFactService {

    private final CandlestickFactRepository candlestickFactRepository;

    /**
     * Get all candlesticks.
     *
     * @return list of all candlesticks
     */
    public List<CandlestickFactDTO> findAll() {
        return candlestickFactRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get candlesticks by data feed UUID.
     *
     * @param dataFeedUuid the data feed UUID
     * @return list of candlesticks
     */
    public List<CandlestickFactDTO> findByDataFeedUuid(UUID dataFeedUuid) {
        return candlestickFactRepository.findByIdDataFeedUuid(dataFeedUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get candlesticks by trade asset.
     *
     * @param tradeAsset the trade asset
     * @return list of candlesticks
     */
    public List<CandlestickFactDTO> findByTradeAsset(TradeAssetType tradeAsset) {
        return candlestickFactRepository.findByIdTradeAsset(tradeAsset).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get candlesticks by trade time frame.
     *
     * @param tradeTimeFrame the trade time frame
     * @return list of candlesticks
     */
    public List<CandlestickFactDTO> findByTradeTimeFrame(TradeTimeFrameType tradeTimeFrame) {
        return candlestickFactRepository.findByIdTradeTimeFrame(tradeTimeFrame).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get candlesticks by datetime ID.
     *
     * @param datetimeId the datetime ID
     * @return list of candlesticks
     */
    public List<CandlestickFactDTO> findByDatetimeId(Long datetimeId) {
        return candlestickFactRepository.findByIdDatetimeId(datetimeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get candlesticks by data feed UUID, trade asset, and trade time frame.
     *
     * @param dataFeedUuid the data feed UUID
     * @param tradeAsset the trade asset
     * @param tradeTimeFrame the trade time frame
     * @return list of candlesticks
     */
    public List<CandlestickFactDTO> findByDataFeedUuidAndTradeAssetAndTradeTimeFrame(
            UUID dataFeedUuid, TradeAssetType tradeAsset, TradeTimeFrameType tradeTimeFrame) {
        return candlestickFactRepository.findByIdDataFeedUuidAndIdTradeAssetAndIdTradeTimeFrame(
                dataFeedUuid, tradeAsset, tradeTimeFrame).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new candlestick.
     *
     * @param candlestickFactDTO the candlestick to create
     * @return the created candlestick
     */
    @Transactional
    public CandlestickFactDTO create(CandlestickFactDTO candlestickFactDTO) {
        CandlestickFact candlestickFact = convertToEntity(candlestickFactDTO);
        CandlestickFact savedCandlestickFact = candlestickFactRepository.save(candlestickFact);
        return convertToDTO(savedCandlestickFact);
    }

    /**
     * Update an existing candlestick.
     *
     * @param dataFeedUuid the data feed UUID
     * @param tradeAsset the trade asset
     * @param tradeTimeFrame the trade time frame
     * @param datetimeId the datetime ID
     * @param candlestickFactDTO the candlestick data to update
     * @return the updated candlestick
     * @throws ResourceNotFoundException if candlestick not found
     */
    @Transactional
    public CandlestickFactDTO update(UUID dataFeedUuid, TradeAssetType tradeAsset,
                                    TradeTimeFrameType tradeTimeFrame, Long datetimeId,
                                    CandlestickFactDTO candlestickFactDTO) {
        CandlestickFact.CandlestickFactId id = new CandlestickFact.CandlestickFactId(
                dataFeedUuid, tradeAsset, tradeTimeFrame, datetimeId);
        
        return candlestickFactRepository.findById(id)
                .map(existingCandlestick -> {
                    existingCandlestick.setOpen(candlestickFactDTO.getOpen());
                    existingCandlestick.setHigh(candlestickFactDTO.getHigh());
                    existingCandlestick.setLow(candlestickFactDTO.getLow());
                    existingCandlestick.setClose(candlestickFactDTO.getClose());
                    existingCandlestick.setVolume(candlestickFactDTO.getVolume());
                    return convertToDTO(candlestickFactRepository.save(existingCandlestick));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Candlestick not found with the given composite key"));
    }

    /**
     * Delete a candlestick by composite key.
     *
     * @param dataFeedUuid the data feed UUID
     * @param tradeAsset the trade asset
     * @param tradeTimeFrame the trade time frame
     * @param datetimeId the datetime ID
     * @throws ResourceNotFoundException if candlestick not found
     */
    @Transactional
    public void delete(UUID dataFeedUuid, TradeAssetType tradeAsset,
                      TradeTimeFrameType tradeTimeFrame, Long datetimeId) {
        CandlestickFact.CandlestickFactId id = new CandlestickFact.CandlestickFactId(
                dataFeedUuid, tradeAsset, tradeTimeFrame, datetimeId);
        
        if (!candlestickFactRepository.existsById(id)) {
            throw new ResourceNotFoundException("Candlestick not found with the given composite key");
        }
        candlestickFactRepository.deleteById(id);
    }

    /**
     * Convert entity to DTO.
     *
     * @param candlestickFact the entity
     * @return the DTO
     */
    private CandlestickFactDTO convertToDTO(CandlestickFact candlestickFact) {
        CandlestickFactDTO dto = new CandlestickFactDTO();
        dto.setDataFeedUuid(candlestickFact.getId().getDataFeedUuid());
        dto.setTradeAsset(candlestickFact.getId().getTradeAsset());
        dto.setTradeTimeFrame(candlestickFact.getId().getTradeTimeFrame());
        dto.setDatetimeId(candlestickFact.getId().getDatetimeId());
        dto.setOpen(candlestickFact.getOpen());
        dto.setHigh(candlestickFact.getHigh());
        dto.setLow(candlestickFact.getLow());
        dto.setClose(candlestickFact.getClose());
        dto.setVolume(candlestickFact.getVolume());
        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param candlestickFactDTO the DTO
     * @return the entity
     */
    private CandlestickFact convertToEntity(CandlestickFactDTO candlestickFactDTO) {
        CandlestickFact entity = new CandlestickFact();
        CandlestickFact.CandlestickFactId id = new CandlestickFact.CandlestickFactId(
                candlestickFactDTO.getDataFeedUuid(),
                candlestickFactDTO.getTradeAsset(),
                candlestickFactDTO.getTradeTimeFrame(),
                candlestickFactDTO.getDatetimeId());
        entity.setId(id);
        entity.setOpen(candlestickFactDTO.getOpen());
        entity.setHigh(candlestickFactDTO.getHigh());
        entity.setLow(candlestickFactDTO.getLow());
        entity.setClose(candlestickFactDTO.getClose());
        entity.setVolume(candlestickFactDTO.getVolume());
        return entity;
    }
}
