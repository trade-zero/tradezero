package com.trading.api.repository;

import com.trading.api.model.PortfolioFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for PortfolioFact entity.
 */
@Repository
public interface PortfolioFactRepository extends JpaRepository<PortfolioFact, UUID> {
    
    /**
     * Find all portfolios by trade zero fact UUID.
     *
     * @param tradeZeroFactUuid the trade zero fact UUID
     * @return a list of portfolios
     */
    List<PortfolioFact> findByTradeZeroFact_TradeZeroFactUuid(UUID tradeZeroFactUuid);
    
    /**
     * Find all portfolios by name.
     *
     * @param name the name to search for
     * @return a list of portfolios
     */
    List<PortfolioFact> findByName(String name);
    
    /**
     * Find all portfolios containing the given text in description.
     *
     * @param description the description text to search for
     * @return a list of portfolios
     */
    List<PortfolioFact> findByDescriptionContaining(String description);
}
