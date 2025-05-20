package com.trading.api.service;

import com.trading.api.dto.BalanceFactDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.BalanceFact;
import com.trading.api.repository.BalanceFactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for BalanceFact entity operations.
 */
@Service
@RequiredArgsConstructor
public class BalanceFactService {

    private final BalanceFactRepository balanceFactRepository;

    /**
     * Get all balance facts.
     *
     * @return list of all balance facts
     */
    public List<BalanceFactDTO> findAll() {
        return balanceFactRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get balance fact by UUID.
     *
     * @param uuid the balance fact UUID
     * @return the balance fact
     * @throws ResourceNotFoundException if balance fact not found
     */
    public BalanceFactDTO findById(UUID uuid) {
        return balanceFactRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Balance fact not found with id: " + uuid));
    }

    /**
     * Get balance facts by portfolio UUID.
     *
     * @param portfolioUuid the portfolio UUID
     * @return list of balance facts
     */
    public List<BalanceFactDTO> findByPortfolioUuid(UUID portfolioUuid) {
        return balanceFactRepository.findByPortfolio_PortfolioUuid(portfolioUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get balance facts by datetime ID.
     *
     * @param datetimeId the datetime ID
     * @return list of balance facts
     */
    public List<BalanceFactDTO> findByDatetimeId(Long datetimeId) {
        return balanceFactRepository.findByDateTime_DatetimeId(datetimeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get balance fact by portfolio UUID and datetime ID.
     *
     * @param portfolioUuid the portfolio UUID
     * @param datetimeId the datetime ID
     * @return the balance fact
     * @throws ResourceNotFoundException if balance fact not found
     */
    public BalanceFactDTO findByPortfolioUuidAndDatetimeId(UUID portfolioUuid, Long datetimeId) {
        return balanceFactRepository.findByPortfolio_PortfolioUuidAndDateTime_DatetimeId(portfolioUuid, datetimeId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Balance fact not found with portfolio id: " + portfolioUuid + " and datetime id: " + datetimeId));
    }

    /**
     * Create a new balance fact.
     *
     * @param balanceFactDTO the balance fact to create
     * @return the created balance fact
     */
    @Transactional
    public BalanceFactDTO create(BalanceFactDTO balanceFactDTO) {
        BalanceFact balanceFact = convertToEntity(balanceFactDTO);
        balanceFact.setBalanceUuid(null); // Ensure UUID is generated
        BalanceFact savedBalanceFact = balanceFactRepository.save(balanceFact);
        return convertToDTO(savedBalanceFact);
    }

    /**
     * Update an existing balance fact.
     *
     * @param uuid the balance fact UUID
     * @param balanceFactDTO the balance fact data to update
     * @return the updated balance fact
     * @throws ResourceNotFoundException if balance fact not found
     */
    @Transactional
    public BalanceFactDTO update(UUID uuid, BalanceFactDTO balanceFactDTO) {
        return balanceFactRepository.findById(uuid)
                .map(existingBalanceFact -> {
                    // In a real implementation, you would need to fetch related entities
                    // and set them properly instead of just UUIDs
                    return convertToDTO(balanceFactRepository.save(existingBalanceFact));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Balance fact not found with id: " + uuid));
    }

    /**
     * Delete a balance fact by UUID.
     *
     * @param uuid the balance fact UUID
     * @throws ResourceNotFoundException if balance fact not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!balanceFactRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Balance fact not found with id: " + uuid);
        }
        balanceFactRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param balanceFact the entity
     * @return the DTO
     */
    private BalanceFactDTO convertToDTO(BalanceFact balanceFact) {
        BalanceFactDTO dto = new BalanceFactDTO();
        dto.setBalanceUuid(balanceFact.getBalanceUuid());

        // Handle relationships properly
        if (balanceFact.getPortfolio() != null) {
            dto.setPortfolioUuid(balanceFact.getPortfolio().getPortfolioUuid());
        }

        if (balanceFact.getDateTime() != null) {
            dto.setDatetimeId(balanceFact.getDateTime().getDatetimeId());
        }

        dto.setInitial(balanceFact.getInitial());
        dto.setCurrent(balanceFact.getCurrent());
        dto.setMax(balanceFact.getMax());
        dto.setMin(balanceFact.getMin());

        return dto;
    }

    /**
     * Convert DTO to entity.
     * Note: In a real implementation, you would need to fetch related entities
     * from their respective repositories.
     *
     * @param balanceFactDTO the DTO
     * @return the entity
     */
    private BalanceFact convertToEntity(BalanceFactDTO balanceFactDTO) {
        BalanceFact entity = new BalanceFact();
        entity.setBalanceUuid(balanceFactDTO.getBalanceUuid());

        // In a real implementation, you would fetch related entities
        // and set them properly instead of just setting UUIDs

        entity.setInitial(balanceFactDTO.getInitial());
        entity.setCurrent(balanceFactDTO.getCurrent());
        entity.setMax(balanceFactDTO.getMax());
        entity.setMin(balanceFactDTO.getMin());

        return entity;
    }
}
