package com.trading.api.repository;

import com.trading.api.model.PositionFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for PositionFact entity.
 */
@Repository
public interface PositionFactRepository extends JpaRepository<PositionFact, UUID> {
    
    /**
     * Find all positions by portfolio UUID.
     *
     * @param portfolioUuid the portfolio UUID
     * @return a list of positions
     */
    List<PositionFact> findByPortfolio_PortfolioUuid(UUID portfolioUuid);
    
    /**
     * Find all positions by position dimension UUID.
     *
     * @param positionDimUuid the position dimension UUID
     * @return a list of positions
     */
    List<PositionFact> findByPositionDim_PositionDimUuid(UUID positionDimUuid);
    
    /**
     * Find all positions by datetime ID.
     *
     * @param datetimeId the datetime ID
     * @return a list of positions
     */
    List<PositionFact> findByDateTime_DatetimeId(Long datetimeId);
    
    /**
     * Find all positions by entry price greater than the specified value.
     *
     * @param entryPrice the minimum entry price
     * @return a list of positions
     */
    List<PositionFact> findByEntryPriceGreaterThan(Double entryPrice);
    
    /**
     * Find all positions by entry price less than the specified value.
     *
     * @param entryPrice the maximum entry price
     * @return a list of positions
     */
    List<PositionFact> findByEntryPriceLessThan(Double entryPrice);
}
