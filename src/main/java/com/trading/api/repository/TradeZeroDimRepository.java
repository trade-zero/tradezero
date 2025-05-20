package com.trading.api.repository;

import com.trading.api.model.TradeZeroDim;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeTimeFrameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for TradeZeroDim entity.
 */
@Repository
public interface TradeZeroDimRepository extends JpaRepository<TradeZeroDim, UUID> {
    
    /**
     * Find all trade zero dimensions by trade time frame.
     *
     * @param tradeTimeFrame the trade time frame
     * @return a list of trade zero dimensions
     */
    List<TradeZeroDim> findByTradeTimeFrame(TradeTimeFrameType tradeTimeFrame);
}
