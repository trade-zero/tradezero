package com.trading.api.service;

import com.trading.api.dto.OrderFactDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.*;
import com.trading.api.model.enums.OrderStatusType;
import com.trading.api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for OrderFact entity operations.
 */
@Service
@RequiredArgsConstructor
public class OrderFactService {

    private final OrderFactRepository orderFactRepository;
    private final OrderDimRepository orderDimRepository;
    private final OrderVenueDimRepository orderVenueDimRepository;
    private final DateTimeDimRepository dateTimeDimRepository;
    private final PortfolioFactRepository portfolioFactRepository;

    /**
     * Get all order facts.
     *
     * @return list of all order facts
     */
    public List<OrderFactDTO> findAll() {
        return orderFactRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get order fact by UUID.
     *
     * @param uuid the order fact UUID
     * @return the order fact
     * @throws ResourceNotFoundException if order fact not found
     */
    public OrderFactDTO findById(UUID uuid) {
        return orderFactRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order fact not found with id: " + uuid));
    }

    /**
     * Get order facts by order dimension UUID.
     *
     * @param orderDimUuid the order dimension UUID
     * @return list of order facts
     */
    public List<OrderFactDTO> findByOrderDimUuid(UUID orderDimUuid) {
        return orderFactRepository.findByOrderDim_OrderDimUuid(orderDimUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get order facts by order venue dimension UUID.
     *
     * @param orderVenueDimUuid the order venue dimension UUID
     * @return list of order facts
     */
    public List<OrderFactDTO> findByOrderVenueDimUuid(UUID orderVenueDimUuid) {
        return orderFactRepository.findByOrderVenueDim_OrderVenueDimUuid(orderVenueDimUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get order facts by datetime ID.
     *
     * @param datetimeId the datetime ID
     * @return list of order facts
     */
    public List<OrderFactDTO> findByDatetimeId(Long datetimeId) {
        return orderFactRepository.findByDateTime_DatetimeId(datetimeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get order facts by portfolio UUID.
     *
     * @param portfolioUuid the portfolio UUID
     * @return list of order facts
     */
    public List<OrderFactDTO> findByPortfolioUuid(UUID portfolioUuid) {
        return orderFactRepository.findByPortfolio_PortfolioUuid(portfolioUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get order facts by order status.
     *
     * @param orderStatus the order status
     * @return list of order facts
     */
    public List<OrderFactDTO> findByOrderStatus(OrderStatusType orderStatus) {
        return orderFactRepository.findByOrderStatus(orderStatus).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new order fact.
     *
     * @param orderFactDTO the order fact to create
     * @return the created order fact
     */
    @Transactional
    public OrderFactDTO create(OrderFactDTO orderFactDTO) {
        OrderFact orderFact = convertToEntity(orderFactDTO);
        orderFact.setOrderFactUuid(null); // Ensure UUID is generated
        OrderFact savedOrderFact = orderFactRepository.save(orderFact);
        return convertToDTO(savedOrderFact);
    }

    /**
     * Update an existing order fact.
     *
     * @param uuid the order fact UUID
     * @param orderFactDTO the order fact data to update
     * @return the updated order fact
     * @throws ResourceNotFoundException if order fact not found
     */
    @Transactional
    public OrderFactDTO update(UUID uuid, OrderFactDTO orderFactDTO) {
        return orderFactRepository.findById(uuid)
                .map(existingOrderFact -> {
                    // Find related entities
                    OrderDim orderDim = orderDimRepository.findById(orderFactDTO.getOrderDimUuid())
                            .orElseThrow(() -> new ResourceNotFoundException("Order dimension not found with id: " +
                                    orderFactDTO.getOrderDimUuid()));

                    OrderVenueDim orderVenueDim = orderVenueDimRepository.findById(orderFactDTO.getOrderVenueDimUuid())
                            .orElseThrow(() -> new ResourceNotFoundException("Order venue dimension not found with id: " +
                                    orderFactDTO.getOrderVenueDimUuid()));

                    DateTimeDim dateTime = dateTimeDimRepository.findById(orderFactDTO.getDatetimeId())
                            .orElseThrow(() -> new ResourceNotFoundException("DateTime dimension not found with id: " +
                                    orderFactDTO.getDatetimeId()));

                    PortfolioFact portfolio = portfolioFactRepository.findById(orderFactDTO.getPortfolioUuid())
                            .orElseThrow(() -> new ResourceNotFoundException("Portfolio fact not found with id: " +
                                    orderFactDTO.getPortfolioUuid()));

                    // Update entity with related entities and values
                    existingOrderFact.setOrderDim(orderDim);
                    existingOrderFact.setOrderVenueDim(orderVenueDim);
                    existingOrderFact.setDateTime(dateTime);
                    existingOrderFact.setPortfolio(portfolio);
                    existingOrderFact.setOrderStatus(orderFactDTO.getOrderStatus());
                    existingOrderFact.setExecutedPrice(orderFactDTO.getExecutedPrice());
                    existingOrderFact.setLimitPrice(orderFactDTO.getLimitPrice());
                    existingOrderFact.setStopPrice(orderFactDTO.getStopPrice());
                    existingOrderFact.setFees(orderFactDTO.getFees());
                    existingOrderFact.setSlippage(orderFactDTO.getSlippage());
                    existingOrderFact.setLatencyMs(orderFactDTO.getLatencyMs());

                    return convertToDTO(orderFactRepository.save(existingOrderFact));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Order fact not found with id: " + uuid));
    }

    /**
     * Delete an order fact by UUID.
     *
     * @param uuid the order fact UUID
     * @throws ResourceNotFoundException if order fact not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!orderFactRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Order fact not found with id: " + uuid);
        }
        orderFactRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param orderFact the entity
     * @return the DTO
     */
    private OrderFactDTO convertToDTO(OrderFact orderFact) {
        OrderFactDTO dto = new OrderFactDTO();
        dto.setOrderFactUuid(orderFact.getOrderFactUuid());

        // Extract UUIDs from related entities
        if (orderFact.getOrderDim() != null) {
            dto.setOrderDimUuid(orderFact.getOrderDim().getOrderDimUuid());
        }

        if (orderFact.getOrderVenueDim() != null) {
            dto.setOrderVenueDimUuid(orderFact.getOrderVenueDim().getOrderVenueDimUuid());
        }

        if (orderFact.getDateTime() != null) {
            dto.setDatetimeId(orderFact.getDateTime().getDatetimeId());
        }

        if (orderFact.getPortfolio() != null) {
            dto.setPortfolioUuid(orderFact.getPortfolio().getPortfolioUuid());
        }

        dto.setOrderStatus(orderFact.getOrderStatus());
        dto.setExecutedPrice(orderFact.getExecutedPrice());
        dto.setLimitPrice(orderFact.getLimitPrice());
        dto.setStopPrice(orderFact.getStopPrice());
        dto.setFees(orderFact.getFees());
        dto.setSlippage(orderFact.getSlippage());
        dto.setLatencyMs(orderFact.getLatencyMs());

        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param orderFactDTO the DTO
     * @return the entity
     */
    private OrderFact convertToEntity(OrderFactDTO orderFactDTO) {
        OrderFact entity = new OrderFact();
        entity.setOrderFactUuid(orderFactDTO.getOrderFactUuid());

        // Set related entities if UUIDs are provided
        if (orderFactDTO.getOrderDimUuid() != null) {
            OrderDim orderDim = orderDimRepository.findById(orderFactDTO.getOrderDimUuid())
                    .orElseThrow(() -> new ResourceNotFoundException("Order dimension not found with id: " +
                            orderFactDTO.getOrderDimUuid()));
            entity.setOrderDim(orderDim);
        }

        if (orderFactDTO.getOrderVenueDimUuid() != null) {
            OrderVenueDim orderVenueDim = orderVenueDimRepository.findById(orderFactDTO.getOrderVenueDimUuid())
                    .orElseThrow(() -> new ResourceNotFoundException("Order venue dimension not found with id: " +
                            orderFactDTO.getOrderVenueDimUuid()));
            entity.setOrderVenueDim(orderVenueDim);
        }

        if (orderFactDTO.getDatetimeId() != null) {
            DateTimeDim dateTime = dateTimeDimRepository.findById(orderFactDTO.getDatetimeId())
                    .orElseThrow(() -> new ResourceNotFoundException("DateTime dimension not found with id: " +
                            orderFactDTO.getDatetimeId()));
            entity.setDateTime(dateTime);
        }

        if (orderFactDTO.getPortfolioUuid() != null) {
            PortfolioFact portfolio = portfolioFactRepository.findById(orderFactDTO.getPortfolioUuid())
                    .orElseThrow(() -> new ResourceNotFoundException("Portfolio fact not found with id: " +
                            orderFactDTO.getPortfolioUuid()));
            entity.setPortfolio(portfolio);
        }

        entity.setOrderStatus(orderFactDTO.getOrderStatus());
        entity.setExecutedPrice(orderFactDTO.getExecutedPrice());
        entity.setLimitPrice(orderFactDTO.getLimitPrice());
        entity.setStopPrice(orderFactDTO.getStopPrice());
        entity.setFees(orderFactDTO.getFees());
        entity.setSlippage(orderFactDTO.getSlippage());
        entity.setLatencyMs(orderFactDTO.getLatencyMs());

        return entity;
    }
}
