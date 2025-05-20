package com.trading.api.repository;

import com.trading.api.model.StockDim;
import com.trading.api.model.enums.TradeAssetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for StockDim entity.
 */
@Repository
public interface StockDimRepository extends JpaRepository<StockDim, UUID> {
    
    /**
     * Find a stock by its asset type.
     *
     * @param assetType the asset type to search for
     * @return an Optional containing the stock if found
     */
    Optional<StockDim> findByAssetType(TradeAssetType assetType);
}
