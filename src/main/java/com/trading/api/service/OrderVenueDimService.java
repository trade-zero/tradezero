package com.trading.api.service;

import com.trading.api.dto.OrderVenueDimDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.OrderVenueDim;
import com.trading.api.repository.OrderVenueDimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for OrderVenueDim entity operations.
 */
@Service
@RequiredArgsConstructor
public class OrderVenueDimService {

    private final OrderVenueDimRepository orderVenueDimRepository;

    /**
     * Get all order venues.
     *
     * @return list of all order venues
     */
    public List<OrderVenueDimDTO> findAll() {
        return orderVenueDimRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get order venue by UUID.
     *
     * @param uuid the order venue UUID
     * @return the order venue
     * @throws ResourceNotFoundException if order venue not found
     */
    public OrderVenueDimDTO findById(UUID uuid) {
        return orderVenueDimRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order venue not found with id: " + uuid));
    }

    /**
     * Get order venues by exchange.
     *
     * @param exchange the exchange
     * @return list of order venues
     */
    public List<OrderVenueDimDTO> findByExchange(String exchange) {
        return orderVenueDimRepository.findByExchange(exchange).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get order venues by broker.
     *
     * @param broker the broker
     * @return list of order venues
     */
    public List<OrderVenueDimDTO> findByBroker(String broker) {
        return orderVenueDimRepository.findByBroker(broker).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get order venues by platform.
     *
     * @param platform the platform
     * @return list of order venues
     */
    public List<OrderVenueDimDTO> findByPlatform(String platform) {
        return orderVenueDimRepository.findByPlatform(platform).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new order venue.
     *
     * @param orderVenueDimDTO the order venue to create
     * @return the created order venue
     */
    @Transactional
    public OrderVenueDimDTO create(OrderVenueDimDTO orderVenueDimDTO) {
        OrderVenueDim orderVenueDim = convertToEntity(orderVenueDimDTO);
        orderVenueDim.setOrderVenueDimUuid(null); // Ensure UUID is generated
        OrderVenueDim savedOrderVenueDim = orderVenueDimRepository.save(orderVenueDim);
        return convertToDTO(savedOrderVenueDim);
    }

    /**
     * Update an existing order venue.
     *
     * @param uuid the order venue UUID
     * @param orderVenueDimDTO the order venue data to update
     * @return the updated order venue
     * @throws ResourceNotFoundException if order venue not found
     */
    @Transactional
    public OrderVenueDimDTO update(UUID uuid, OrderVenueDimDTO orderVenueDimDTO) {
        return orderVenueDimRepository.findById(uuid)
                .map(existingOrderVenue -> {
                    existingOrderVenue.setExchange(orderVenueDimDTO.getExchange());
                    existingOrderVenue.setBroker(orderVenueDimDTO.getBroker());
                    existingOrderVenue.setPlatform(orderVenueDimDTO.getPlatform());
                    return convertToDTO(orderVenueDimRepository.save(existingOrderVenue));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Order venue not found with id: " + uuid));
    }

    /**
     * Delete an order venue by UUID.
     *
     * @param uuid the order venue UUID
     * @throws ResourceNotFoundException if order venue not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!orderVenueDimRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Order venue not found with id: " + uuid);
        }
        orderVenueDimRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param orderVenueDim the entity
     * @return the DTO
     */
    private OrderVenueDimDTO convertToDTO(OrderVenueDim orderVenueDim) {
        OrderVenueDimDTO dto = new OrderVenueDimDTO();
        dto.setOrderVenueDimUuid(orderVenueDim.getOrderVenueDimUuid());
        dto.setExchange(orderVenueDim.getExchange());
        dto.setBroker(orderVenueDim.getBroker());
        dto.setPlatform(orderVenueDim.getPlatform());
        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param orderVenueDimDTO the DTO
     * @return the entity
     */
    private OrderVenueDim convertToEntity(OrderVenueDimDTO orderVenueDimDTO) {
        OrderVenueDim entity = new OrderVenueDim();
        entity.setOrderVenueDimUuid(orderVenueDimDTO.getOrderVenueDimUuid());
        entity.setExchange(orderVenueDimDTO.getExchange());
        entity.setBroker(orderVenueDimDTO.getBroker());
        entity.setPlatform(orderVenueDimDTO.getPlatform());
        return entity;
    }
}
