package com.trading.api.repository;

import com.trading.api.model.BalanceFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for BalanceFact entity.
 */
@Repository
public interface BalanceFactRepository extends JpaRepository<BalanceFact, UUID> {
    
    /**
     * Find all balances by portfolio UUID.
     *
     * @param portfolioUuid the portfolio UUID
     * @return a list of balances
     */
    List<BalanceFact> findByPortfolio_PortfolioUuid(UUID portfolioUuid);
    
    /**
     * Find all balances by datetime ID.
     *
     * @param datetimeId the datetime ID
     * @return a list of balances
     */
    List<BalanceFact> findByDateTime_DatetimeId(Long datetimeId);
    
    /**
     * Find balance by portfolio UUID and datetime ID.
     *
     * @param portfolioUuid the portfolio UUID
     * @param datetimeId the datetime ID
     * @return an Optional containing the balance if found
     */
    Optional<BalanceFact> findByPortfolio_PortfolioUuidAndDateTime_DatetimeId(UUID portfolioUuid, Long datetimeId);
}
