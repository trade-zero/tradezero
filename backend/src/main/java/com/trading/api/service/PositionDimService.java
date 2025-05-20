package com.trading.api.service;

import com.trading.api.dto.PositionDimDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.PositionDim;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import com.trading.api.repository.PositionDimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for PositionDim entity operations.
 */
@Service
@RequiredArgsConstructor
public class PositionDimService {

    private final PositionDimRepository positionDimRepository;

    /**
     * Get all position dimensions.
     *
     * @return list of all position dimensions
     */
    public List<PositionDimDTO> findAll() {
        return positionDimRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get position dimension by UUID.
     *
     * @param uuid the position dimension UUID
     * @return the position dimension
     * @throws ResourceNotFoundException if position dimension not found
     */
    public PositionDimDTO findById(UUID uuid) {
        return positionDimRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Position dimension not found with id: " + uuid));
    }

    /**
     * Get position dimensions by direction type.
     *
     * @param directionType the direction type
     * @return list of position dimensions
     */
    public List<PositionDimDTO> findByDirectionType(TradeDirectionType directionType) {
        return positionDimRepository.findByDirectionType(directionType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get position dimensions by asset type.
     *
     * @param assetType the asset type
     * @return list of position dimensions
     */
    public List<PositionDimDTO> findByAssetType(TradeAssetType assetType) {
        return positionDimRepository.findByAssetType(assetType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new position dimension.
     *
     * @param positionDimDTO the position dimension to create
     * @return the created position dimension
     */
    @Transactional
    public PositionDimDTO create(PositionDimDTO positionDimDTO) {
        PositionDim positionDim = convertToEntity(positionDimDTO);
        positionDim.setPositionDimUuid(null); // Ensure UUID is generated
        PositionDim savedPositionDim = positionDimRepository.save(positionDim);
        return convertToDTO(savedPositionDim);
    }

    /**
     * Update an existing position dimension.
     *
     * @param uuid the position dimension UUID
     * @param positionDimDTO the position dimension data to update
     * @return the updated position dimension
     * @throws ResourceNotFoundException if position dimension not found
     */
    @Transactional
    public PositionDimDTO update(UUID uuid, PositionDimDTO positionDimDTO) {
        return positionDimRepository.findById(uuid)
                .map(existingPositionDim -> {
                    existingPositionDim.setDirectionType(positionDimDTO.getDirectionType());
                    existingPositionDim.setAssetType(positionDimDTO.getAssetType());
                    return convertToDTO(positionDimRepository.save(existingPositionDim));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Position dimension not found with id: " + uuid));
    }

    /**
     * Delete a position dimension by UUID.
     *
     * @param uuid the position dimension UUID
     * @throws ResourceNotFoundException if position dimension not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!positionDimRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Position dimension not found with id: " + uuid);
        }
        positionDimRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param positionDim the entity
     * @return the DTO
     */
    private PositionDimDTO convertToDTO(PositionDim positionDim) {
        PositionDimDTO dto = new PositionDimDTO();
        dto.setPositionDimUuid(positionDim.getPositionDimUuid());
        dto.setDirectionType(positionDim.getDirectionType());
        dto.setAssetType(positionDim.getAssetType());
        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param positionDimDTO the DTO
     * @return the entity
     */
    private PositionDim convertToEntity(PositionDimDTO positionDimDTO) {
        PositionDim entity = new PositionDim();
        entity.setPositionDimUuid(positionDimDTO.getPositionDimUuid());
        entity.setDirectionType(positionDimDTO.getDirectionType());
        entity.setAssetType(positionDimDTO.getAssetType());
        return entity;
    }
}
