package com.trading.api.repository;

import com.trading.api.model.TradeZeroFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for TradeZeroFact entity.
 */
@Repository
public interface TradeZeroFactRepository extends JpaRepository<TradeZeroFact, UUID> {
    
    /**
     * Find all trade zero facts by trade zero dimension UUID.
     *
     * @param tradeZeroDimUuid the trade zero dimension UUID
     * @return a list of trade zero facts
     */
    List<TradeZeroFact> findByTradeZeroDim_TradeZeroDimUuid(UUID tradeZeroDimUuid);
    
    /**
     * Find all trade zero facts by agent dimension UUID.
     *
     * @param agentDimUuid the agent dimension UUID
     * @return a list of trade zero facts
     */
    List<TradeZeroFact> findByAgentDim_AgentDimUuid(UUID agentDimUuid);
    
    /**
     * Find all trade zero facts by epoch.
     *
     * @param epoch the epoch
     * @return a list of trade zero facts
     */
    List<TradeZeroFact> findByEpoch(Integer epoch);
    
    /**
     * Find all trade zero facts by trained status.
     *
     * @param trained the trained status
     * @return a list of trade zero facts
     */
    List<TradeZeroFact> findByTrained(Boolean trained);
}
