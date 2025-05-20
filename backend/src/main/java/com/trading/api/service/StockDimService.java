package com.trading.api.service;

import com.trading.api.dto.StockDimDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.StockDim;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.repository.StockDimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for StockDim entity operations.
 */
@Service
@RequiredArgsConstructor
public class StockDimService {

    private final StockDimRepository stockDimRepository;

    /**
     * Get all stocks.
     *
     * @return list of all stocks
     */
    public List<StockDimDTO> findAll() {
        return stockDimRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get stock by UUID.
     *
     * @param uuid the stock UUID
     * @return the stock
     * @throws ResourceNotFoundException if stock not found
     */
    public StockDimDTO findById(UUID uuid) {
        return stockDimRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + uuid));
    }

    /**
     * Get stock by asset type.
     *
     * @param assetType the asset type
     * @return the stock
     * @throws ResourceNotFoundException if stock not found
     */
    public StockDimDTO findByAssetType(TradeAssetType assetType) {
        return stockDimRepository.findByAssetType(assetType)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with asset type: " + assetType));
    }

    /**
     * Create a new stock.
     *
     * @param stockDimDTO the stock to create
     * @return the created stock
     */
    @Transactional
    public StockDimDTO create(StockDimDTO stockDimDTO) {
        StockDim stockDim = convertToEntity(stockDimDTO);
        stockDim.setStockUuid(null); // Ensure UUID is generated
        StockDim savedStockDim = stockDimRepository.save(stockDim);
        return convertToDTO(savedStockDim);
    }

    /**
     * Update an existing stock.
     *
     * @param uuid the stock UUID
     * @param stockDimDTO the stock data to update
     * @return the updated stock
     * @throws ResourceNotFoundException if stock not found
     */
    @Transactional
    public StockDimDTO update(UUID uuid, StockDimDTO stockDimDTO) {
        return stockDimRepository.findById(uuid)
                .map(existingStock -> {
                    existingStock.setAssetType(stockDimDTO.getAssetType());
                    existingStock.setName(stockDimDTO.getName());
                    existingStock.setTickSize(stockDimDTO.getTickSize());
                    existingStock.setTickValue(stockDimDTO.getTickValue());
                    existingStock.setVolumeSize(stockDimDTO.getVolumeSize());
                    return convertToDTO(stockDimRepository.save(existingStock));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + uuid));
    }

    /**
     * Delete a stock by UUID.
     *
     * @param uuid the stock UUID
     * @throws ResourceNotFoundException if stock not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!stockDimRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Stock not found with id: " + uuid);
        }
        stockDimRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param stockDim the entity
     * @return the DTO
     */
    private StockDimDTO convertToDTO(StockDim stockDim) {
        StockDimDTO dto = new StockDimDTO();
        dto.setStockUuid(stockDim.getStockUuid());
        dto.setAssetType(stockDim.getAssetType());
        dto.setName(stockDim.getName());
        dto.setTickSize(stockDim.getTickSize());
        dto.setTickValue(stockDim.getTickValue());
        dto.setVolumeSize(stockDim.getVolumeSize());
        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param stockDimDTO the DTO
     * @return the entity
     */
    private StockDim convertToEntity(StockDimDTO stockDimDTO) {
        StockDim entity = new StockDim();
        entity.setStockUuid(stockDimDTO.getStockUuid());
        entity.setAssetType(stockDimDTO.getAssetType());
        entity.setName(stockDimDTO.getName());
        entity.setTickSize(stockDimDTO.getTickSize());
        entity.setTickValue(stockDimDTO.getTickValue());
        entity.setVolumeSize(stockDimDTO.getVolumeSize());
        return entity;
    }
}
