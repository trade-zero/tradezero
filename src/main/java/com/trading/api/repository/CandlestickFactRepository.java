package com.trading.api.repository;

import com.trading.api.model.CandlestickFact;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeTimeFrameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for CandlestickFact entity.
 */
@Repository
public interface CandlestickFactRepository extends JpaRepository<CandlestickFact, CandlestickFact.CandlestickFactId> {
    
    /**
     * Find all candlesticks by data feed UUID.
     *
     * @param dataFeedUuid the data feed UUID
     * @return a list of candlesticks
     */
    List<CandlestickFact> findByIdDataFeedUuid(UUID dataFeedUuid);
    
    /**
     * Find all candlesticks by trade asset.
     *
     * @param tradeAsset the trade asset
     * @return a list of candlesticks
     */
    List<CandlestickFact> findByIdTradeAsset(TradeAssetType tradeAsset);
    
    /**
     * Find all candlesticks by trade time frame.
     *
     * @param tradeTimeFrame the trade time frame
     * @return a list of candlesticks
     */
    List<CandlestickFact> findByIdTradeTimeFrame(TradeTimeFrameType tradeTimeFrame);
    
    /**
     * Find all candlesticks by datetime ID.
     *
     * @param datetimeId the datetime ID
     * @return a list of candlesticks
     */
    List<CandlestickFact> findByIdDatetimeId(Long datetimeId);
    
    /**
     * Find all candlesticks by data feed UUID, trade asset, and trade time frame.
     *
     * @param dataFeedUuid the data feed UUID
     * @param tradeAsset the trade asset
     * @param tradeTimeFrame the trade time frame
     * @return a list of candlesticks
     */
    List<CandlestickFact> findByIdDataFeedUuidAndIdTradeAssetAndIdTradeTimeFrame(
            UUID dataFeedUuid, 
            TradeAssetType tradeAsset, 
            TradeTimeFrameType tradeTimeFrame);
}
