package com.trading.api.repository;

import com.trading.api.model.RiskManagementFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for RiskManagementFact entity.
 */
@Repository
public interface RiskManagementFactRepository extends JpaRepository<RiskManagementFact, UUID> {
    
    /**
     * Find all risk management facts by trade zero fact UUID.
     *
     * @param tradeZeroFactUuid the trade zero fact UUID
     * @return a list of risk management facts
     */
    List<RiskManagementFact> findByTradeZeroFact_TradeZeroFactUuid(UUID tradeZeroFactUuid);
    
    /**
     * Find all risk management facts by actions count.
     *
     * @param actions the actions count
     * @return a list of risk management facts
     */
    List<RiskManagementFact> findByActions(Integer actions);
}
