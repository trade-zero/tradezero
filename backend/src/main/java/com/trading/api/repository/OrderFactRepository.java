package com.trading.api.repository;

import com.trading.api.model.OrderFact;
import com.trading.api.model.enums.OrderStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for OrderFact entity.
 */
@Repository
public interface OrderFactRepository extends JpaRepository<OrderFact, UUID> {
    
    /**
     * Find all orders by order dimension UUID.
     *
     * @param orderDimUuid the order dimension UUID
     * @return a list of orders
     */
    List<OrderFact> findByOrderDim_OrderDimUuid(UUID orderDimUuid);
    
    /**
     * Find all orders by order venue dimension UUID.
     *
     * @param orderVenueDimUuid the order venue dimension UUID
     * @return a list of orders
     */
    List<OrderFact> findByOrderVenueDim_OrderVenueDimUuid(UUID orderVenueDimUuid);
    
    /**
     * Find all orders by datetime ID.
     *
     * @param datetimeId the datetime ID
     * @return a list of orders
     */
    List<OrderFact> findByDateTime_DatetimeId(Long datetimeId);
    
    /**
     * Find all orders by portfolio UUID.
     *
     * @param portfolioUuid the portfolio UUID
     * @return a list of orders
     */
    List<OrderFact> findByPortfolio_PortfolioUuid(UUID portfolioUuid);
    
    /**
     * Find all orders by order status.
     *
     * @param orderStatus the order status
     * @return a list of orders
     */
    List<OrderFact> findByOrderStatus(OrderStatusType orderStatus);
}
