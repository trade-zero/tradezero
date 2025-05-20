package com.trading.api.repository;

import com.trading.api.model.PositionDim;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for PositionDim entity.
 */
@Repository
public interface PositionDimRepository extends JpaRepository<PositionDim, UUID> {
    
    /**
     * Find a position by its asset type, direction type and value.
     *
     * @param assetType the asset type
     * @param directionType the direction type
     * @param value the value
     * @return an Optional containing the position if found
     */
    Optional<PositionDim> findByAssetTypeAndDirectionTypeAndValue(
            TradeAssetType assetType, 
            TradeDirectionType directionType, 
            Double value);
    
    /**
     * Find all positions by asset type.
     *
     * @param assetType the asset type
     * @return a list of positions
     */
    List<PositionDim> findByAssetType(TradeAssetType assetType);
    
    /**
     * Find all positions by direction type.
     *
     * @param directionType the direction type
     * @return a list of positions
     */
    List<PositionDim> findByDirectionType(TradeDirectionType directionType);
}
