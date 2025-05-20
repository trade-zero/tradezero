package com.trading.api.service;

import com.trading.api.dto.PortfolioFactDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.PortfolioFact;
import com.trading.api.repository.PortfolioFactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for PortfolioFact entity operations.
 */
@Service
@RequiredArgsConstructor
public class PortfolioFactService {

    private final PortfolioFactRepository portfolioFactRepository;

    /**
     * Get all portfolios.
     *
     * @return list of all portfolios
     */
    public List<PortfolioFactDTO> findAll() {
        return portfolioFactRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get portfolio by UUID.
     *
     * @param uuid the portfolio UUID
     * @return the portfolio
     * @throws ResourceNotFoundException if portfolio not found
     */
    public PortfolioFactDTO findById(UUID uuid) {
        return portfolioFactRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found with id: " + uuid));
    }

    /**
     * Get portfolios by trade zero fact UUID.
     *
     * @param tradeZeroFactUuid the trade zero fact UUID
     * @return list of portfolios
     */
    public List<PortfolioFactDTO> findByTradeZeroFactUuid(UUID tradeZeroFactUuid) {
        return portfolioFactRepository.findByTradeZeroFact_TradeZeroFactUuid(tradeZeroFactUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get portfolios by name.
     *
     * @param name the portfolio name
     * @return list of portfolios
     */
    public List<PortfolioFactDTO> findAllByName(String name) {
        return portfolioFactRepository.findByName(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get single portfolio by name.
     *
     * @param name the portfolio name
     * @return the portfolio
     * @throws ResourceNotFoundException if portfolio not found
     */
    public PortfolioFactDTO findByName(String name) {
        return portfolioFactRepository.findByName(name).stream()
                .findFirst()
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found with name: " + name));
    }

    /**
     * Get portfolios by description containing.
     *
     * @param description the description text to search for
     * @return list of portfolios
     */
    public List<PortfolioFactDTO> findByDescriptionContaining(String description) {
        return portfolioFactRepository.findByDescriptionContaining(description).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new portfolio.
     *
     * @param portfolioFactDTO the portfolio to create
     * @return the created portfolio
     */
    @Transactional
    public PortfolioFactDTO create(PortfolioFactDTO portfolioFactDTO) {
        PortfolioFact portfolioFact = convertToEntity(portfolioFactDTO);
        portfolioFact.setPortfolioUuid(null); // Ensure UUID is generated
        PortfolioFact savedPortfolioFact = portfolioFactRepository.save(portfolioFact);
        return convertToDTO(savedPortfolioFact);
    }

    /**
     * Update an existing portfolio.
     *
     * @param uuid the portfolio UUID
     * @param portfolioFactDTO the portfolio data to update
     * @return the updated portfolio
     * @throws ResourceNotFoundException if portfolio not found
     */
    @Transactional
    public PortfolioFactDTO update(UUID uuid, PortfolioFactDTO portfolioFactDTO) {
        return portfolioFactRepository.findById(uuid)
                .map(existingPortfolio -> {
                    // In a real implementation, you would need to fetch related entities
                    // and set them properly instead of just UUIDs
                    existingPortfolio.setName(portfolioFactDTO.getName());
                    existingPortfolio.setDescription(portfolioFactDTO.getDescription());
                    return convertToDTO(portfolioFactRepository.save(existingPortfolio));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found with id: " + uuid));
    }

    /**
     * Delete a portfolio by UUID.
     *
     * @param uuid the portfolio UUID
     * @throws ResourceNotFoundException if portfolio not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!portfolioFactRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Portfolio not found with id: " + uuid);
        }
        portfolioFactRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param portfolioFact the entity
     * @return the DTO
     */
    private PortfolioFactDTO convertToDTO(PortfolioFact portfolioFact) {
        PortfolioFactDTO dto = new PortfolioFactDTO();
        dto.setPortfolioUuid(portfolioFact.getPortfolioUuid());

        // Handle relationships properly
        if (portfolioFact.getTradeZeroFact() != null) {
            dto.setTradeZeroFactUuid(portfolioFact.getTradeZeroFact().getTradeZeroFactUuid());
        }

        dto.setName(portfolioFact.getName());
        dto.setDescription(portfolioFact.getDescription());
        return dto;
    }

    /**
     * Convert DTO to entity.
     * Note: In a real implementation, you would need to fetch related entities
     * from their respective repositories.
     *
     * @param portfolioFactDTO the DTO
     * @return the entity
     */
    private PortfolioFact convertToEntity(PortfolioFactDTO portfolioFactDTO) {
        PortfolioFact entity = new PortfolioFact();
        entity.setPortfolioUuid(portfolioFactDTO.getPortfolioUuid());

        // In a real implementation, you would fetch related entities
        // and set them properly instead of just setting UUIDs

        entity.setName(portfolioFactDTO.getName());
        entity.setDescription(portfolioFactDTO.getDescription());
        return entity;
    }
}
