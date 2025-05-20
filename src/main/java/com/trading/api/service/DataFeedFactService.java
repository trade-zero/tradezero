package com.trading.api.service;

import com.trading.api.dto.DataFeedFactDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.DataFeedFact;
import com.trading.api.repository.DataFeedFactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for DataFeedFact entity operations.
 */
@Service
@RequiredArgsConstructor
public class DataFeedFactService {

    private final DataFeedFactRepository dataFeedFactRepository;

    /**
     * Get all data feeds.
     *
     * @return list of all data feeds
     */
    public List<DataFeedFactDTO> findAll() {
        return dataFeedFactRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get data feed by UUID.
     *
     * @param uuid the data feed UUID
     * @return the data feed
     * @throws ResourceNotFoundException if data feed not found
     */
    public DataFeedFactDTO findById(UUID uuid) {
        return dataFeedFactRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Data feed not found with id: " + uuid));
    }

    /**
     * Get data feed by name.
     *
     * @param name the data feed name
     * @return the data feed
     * @throws ResourceNotFoundException if data feed not found
     */
    public DataFeedFactDTO findByName(String name) {
        DataFeedFact dataFeedFact = dataFeedFactRepository.findByName(name);
        if (dataFeedFact == null) {
            throw new ResourceNotFoundException("Data feed not found with name: " + name);
        }
        return convertToDTO(dataFeedFact);
    }

    /**
     * Create a new data feed.
     *
     * @param dataFeedFactDTO the data feed to create
     * @return the created data feed
     */
    @Transactional
    public DataFeedFactDTO create(DataFeedFactDTO dataFeedFactDTO) {
        DataFeedFact dataFeedFact = convertToEntity(dataFeedFactDTO);
        if (dataFeedFact.getDataFeedUuid() == null) {
            dataFeedFact.setDataFeedUuid(UUID.randomUUID());
        }
        DataFeedFact savedDataFeedFact = dataFeedFactRepository.save(dataFeedFact);
        return convertToDTO(savedDataFeedFact);
    }

    /**
     * Update an existing data feed.
     *
     * @param uuid the data feed UUID
     * @param dataFeedFactDTO the data feed data to update
     * @return the updated data feed
     * @throws ResourceNotFoundException if data feed not found
     */
    @Transactional
    public DataFeedFactDTO update(UUID uuid, DataFeedFactDTO dataFeedFactDTO) {
        return dataFeedFactRepository.findById(uuid)
                .map(existingDataFeed -> {
                    existingDataFeed.setName(dataFeedFactDTO.getName());
                    return convertToDTO(dataFeedFactRepository.save(existingDataFeed));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Data feed not found with id: " + uuid));
    }

    /**
     * Delete a data feed by UUID.
     *
     * @param uuid the data feed UUID
     * @throws ResourceNotFoundException if data feed not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!dataFeedFactRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Data feed not found with id: " + uuid);
        }
        dataFeedFactRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param dataFeedFact the entity
     * @return the DTO
     */
    private DataFeedFactDTO convertToDTO(DataFeedFact dataFeedFact) {
        DataFeedFactDTO dto = new DataFeedFactDTO();
        dto.setDataFeedUuid(dataFeedFact.getDataFeedUuid());
        dto.setName(dataFeedFact.getName());
        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param dataFeedFactDTO the DTO
     * @return the entity
     */
    private DataFeedFact convertToEntity(DataFeedFactDTO dataFeedFactDTO) {
        DataFeedFact entity = new DataFeedFact();
        entity.setDataFeedUuid(dataFeedFactDTO.getDataFeedUuid());
        entity.setName(dataFeedFactDTO.getName());
        return entity;
    }
}
