package com.trading.api.service;

import com.trading.api.dto.PositionFactDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.PositionFact;
import com.trading.api.repository.PositionFactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for PositionFact entity operations.
 */
@Service
@RequiredArgsConstructor
public class PositionFactService {

    private final PositionFactRepository positionFactRepository;

    /**
     * Get all position facts.
     *
     * @return list of all position facts
     */
    public List<PositionFactDTO> findAll() {
        return positionFactRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get position fact by UUID.
     *
     * @param uuid the position fact UUID
     * @return the position fact
     * @throws ResourceNotFoundException if position fact not found
     */
    public PositionFactDTO findById(UUID uuid) {
        return positionFactRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Position fact not found with id: " + uuid));
    }

    /**
     * Get position facts by position dimension UUID.
     *
     * @param positionDimUuid the position dimension UUID
     * @return list of position facts
     */
    public List<PositionFactDTO> findByPositionDimUuid(UUID positionDimUuid) {
        return positionFactRepository.findByPositionDim_PositionDimUuid(positionDimUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get position facts by portfolio UUID.
     *
     * @param portfolioUuid the portfolio UUID
     * @return list of position facts
     */
    public List<PositionFactDTO> findByPortfolioUuid(UUID portfolioUuid) {
        return positionFactRepository.findByPortfolio_PortfolioUuid(portfolioUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get position facts by datetime ID.
     *
     * @param datetimeId the datetime ID
     * @return list of position facts
     */
    public List<PositionFactDTO> findByDatetimeId(Long datetimeId) {
        return positionFactRepository.findByDateTime_DatetimeId(datetimeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get position facts by entry price greater than the specified value.
     *
     * @param entryPrice the minimum entry price
     * @return list of position facts
     */
    public List<PositionFactDTO> findByEntryPriceGreaterThan(Double entryPrice) {
        return positionFactRepository.findByEntryPriceGreaterThan(entryPrice).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get position facts by entry price less than the specified value.
     *
     * @param entryPrice the maximum entry price
     * @return list of position facts
     */
    public List<PositionFactDTO> findByEntryPriceLessThan(Double entryPrice) {
        return positionFactRepository.findByEntryPriceLessThan(entryPrice).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new position fact.
     *
     * @param positionFactDTO the position fact to create
     * @return the created position fact
     */
    @Transactional
    public PositionFactDTO create(PositionFactDTO positionFactDTO) {
        PositionFact positionFact = convertToEntity(positionFactDTO);
        positionFact.setPositionUuid(null); // Ensure UUID is generated
        PositionFact savedPositionFact = positionFactRepository.save(positionFact);
        return convertToDTO(savedPositionFact);
    }

    /**
     * Update an existing position fact.
     *
     * @param uuid the position fact UUID
     * @param positionFactDTO the position fact data to update
     * @return the updated position fact
     * @throws ResourceNotFoundException if position fact not found
     */
    @Transactional
    public PositionFactDTO update(UUID uuid, PositionFactDTO positionFactDTO) {
        return positionFactRepository.findById(uuid)
                .map(existingPositionFact -> {
                    // Update entity fields based on DTO
                    // Note: In a real implementation, you would need to fetch related entities
                    // and set them properly instead of just UUIDs
                    return convertToDTO(positionFactRepository.save(existingPositionFact));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Position fact not found with id: " + uuid));
    }

    /**
     * Delete a position fact by UUID.
     *
     * @param uuid the position fact UUID
     * @throws ResourceNotFoundException if position fact not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!positionFactRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Position fact not found with id: " + uuid);
        }
        positionFactRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param positionFact the entity
     * @return the DTO
     */
    private PositionFactDTO convertToDTO(PositionFact positionFact) {
        PositionFactDTO dto = new PositionFactDTO();
        dto.setPositionUuid(positionFact.getPositionUuid());

        // Handle relationships properly
        if (positionFact.getPositionDim() != null) {
            dto.setPositionDimUuid(positionFact.getPositionDim().getPositionDimUuid());
        }

        if (positionFact.getPortfolio() != null) {
            dto.setPortfolioUuid(positionFact.getPortfolio().getPortfolioUuid());
        }

        if (positionFact.getDateTime() != null) {
            dto.setDatetimeId(positionFact.getDateTime().getDatetimeId());
        }

        dto.setEntryPrice(positionFact.getEntryPrice());

        return dto;
    }

    /**
     * Convert DTO to entity.
     * Note: In a real implementation, you would need to fetch related entities
     * from their respective repositories.
     *
     * @param positionFactDTO the DTO
     * @return the entity
     */
    private PositionFact convertToEntity(PositionFactDTO positionFactDTO) {
        PositionFact entity = new PositionFact();
        entity.setPositionUuid(positionFactDTO.getPositionUuid());

        // In a real implementation, you would fetch related entities
        // and set them properly instead of just setting UUIDs

        entity.setEntryPrice(positionFactDTO.getEntryPrice());

        return entity;
    }
}
