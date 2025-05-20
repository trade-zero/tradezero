package com.trading.api.repository;

import com.trading.api.model.OrderVenueDim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for OrderVenueDim entity.
 */
@Repository
public interface OrderVenueDimRepository extends JpaRepository<OrderVenueDim, UUID> {
    
    /**
     * Find all order venues by exchange.
     *
     * @param exchange the exchange name
     * @return a list of order venues
     */
    List<OrderVenueDim> findByExchange(String exchange);
    
    /**
     * Find all order venues by broker.
     *
     * @param broker the broker name
     * @return a list of order venues
     */
    List<OrderVenueDim> findByBroker(String broker);
    
    /**
     * Find all order venues by platform.
     *
     * @param platform the platform name
     * @return a list of order venues
     */
    List<OrderVenueDim> findByPlatform(String platform);
    
    /**
     * Find order venue by exchange, broker and platform.
     *
     * @param exchange the exchange name
     * @param broker the broker name
     * @param platform the platform name
     * @return a list of order venues
     */
    List<OrderVenueDim> findByExchangeAndBrokerAndPlatform(String exchange, String broker, String platform);
}
