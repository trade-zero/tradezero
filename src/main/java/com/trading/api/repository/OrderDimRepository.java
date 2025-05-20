package com.trading.api.repository;

import com.trading.api.model.OrderDim;
import com.trading.api.model.enums.OrderType;
import com.trading.api.model.enums.TradeActionType;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for OrderDim entity.
 */
@Repository
public interface OrderDimRepository extends JpaRepository<OrderDim, UUID> {
    
    /**
     * Find an order by its order type, direction type, action type, asset type and volume.
     *
     * @param orderType the order type
     * @param directionType the direction type
     * @param actionType the action type
     * @param assetType the asset type
     * @param volume the volume
     * @return an Optional containing the order if found
     */
    Optional<OrderDim> findByOrderTypeAndDirectionTypeAndActionTypeAndAssetTypeAndVolume(
            OrderType orderType, 
            TradeDirectionType directionType, 
            TradeActionType actionType, 
            TradeAssetType assetType, 
            Double volume);
    
    /**
     * Find all orders by order type.
     *
     * @param orderType the order type
     * @return a list of orders
     */
    List<OrderDim> findByOrderType(OrderType orderType);
    
    /**
     * Find all orders by direction type.
     *
     * @param directionType the direction type
     * @return a list of orders
     */
    List<OrderDim> findByDirectionType(TradeDirectionType directionType);
    
    /**
     * Find all orders by action type.
     *
     * @param actionType the action type
     * @return a list of orders
     */
    List<OrderDim> findByActionType(TradeActionType actionType);
    
    /**
     * Find all orders by asset type.
     *
     * @param assetType the asset type
     * @return a list of orders
     */
    List<OrderDim> findByAssetType(TradeAssetType assetType);
}
