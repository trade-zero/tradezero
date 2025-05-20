package com.trading.api.repository;

import com.trading.api.model.ActionDim;
import com.trading.api.model.enums.TradeActionType;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for ActionDim entity.
 */
@Repository
public interface ActionDimRepository extends JpaRepository<ActionDim, UUID> {
    
    /**
     * Find an action by its asset type, direction type, action type and volume.
     *
     * @param assetType the asset type
     * @param directionType the direction type
     * @param actionType the action type
     * @param volume the volume
     * @return an Optional containing the action if found
     */
    Optional<ActionDim> findByAssetTypeAndDirectionTypeAndActionTypeAndVolume(
            TradeAssetType assetType, 
            TradeDirectionType directionType, 
            TradeActionType actionType, 
            Double volume);
    
    /**
     * Find all actions by asset type.
     *
     * @param assetType the asset type
     * @return a list of actions
     */
    List<ActionDim> findByAssetType(TradeAssetType assetType);
    
    /**
     * Find all actions by direction type.
     *
     * @param directionType the direction type
     * @return a list of actions
     */
    List<ActionDim> findByDirectionType(TradeDirectionType directionType);
    
    /**
     * Find all actions by action type.
     *
     * @param actionType the action type
     * @return a list of actions
     */
    List<ActionDim> findByActionType(TradeActionType actionType);
}
