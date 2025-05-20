package com.trading.api.service;

import com.trading.api.dto.OrderDimDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.OrderDim;
import com.trading.api.model.enums.OrderType;
import com.trading.api.model.enums.TradeActionType;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import com.trading.api.repository.OrderDimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for OrderDim entity operations.
 */
@Service
@RequiredArgsConstructor
public class OrderDimService {

    private final OrderDimRepository orderDimRepository;

    /**
     * Get all order dimensions.
     *
     * @return list of all order dimensions
     */
    public List<OrderDimDTO> findAll() {
        return orderDimRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get order dimension by UUID.
     *
     * @param uuid the order dimension UUID
     * @return the order dimension
     * @throws ResourceNotFoundException if order dimension not found
     */
    public OrderDimDTO findById(UUID uuid) {
        return orderDimRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order dimension not found with id: " + uuid));
    }

    /**
     * Get order dimensions by order type.
     *
     * @param orderType the order type
     * @return list of order dimensions
     */
    public List<OrderDimDTO> findByOrderType(OrderType orderType) {
        return orderDimRepository.findByOrderType(orderType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get order dimensions by direction type.
     *
     * @param directionType the direction type
     * @return list of order dimensions
     */
    public List<OrderDimDTO> findByDirectionType(TradeDirectionType directionType) {
        return orderDimRepository.findByDirectionType(directionType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get order dimensions by action type.
     *
     * @param actionType the action type
     * @return list of order dimensions
     */
    public List<OrderDimDTO> findByActionType(TradeActionType actionType) {
        return orderDimRepository.findByActionType(actionType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get order dimensions by asset type.
     *
     * @param assetType the asset type
     * @return list of order dimensions
     */
    public List<OrderDimDTO> findByAssetType(TradeAssetType assetType) {
        return orderDimRepository.findByAssetType(assetType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new order dimension.
     *
     * @param orderDimDTO the order dimension to create
     * @return the created order dimension
     */
    @Transactional
    public OrderDimDTO create(OrderDimDTO orderDimDTO) {
        OrderDim orderDim = convertToEntity(orderDimDTO);
        orderDim.setOrderDimUuid(null); // Ensure UUID is generated
        OrderDim savedOrderDim = orderDimRepository.save(orderDim);
        return convertToDTO(savedOrderDim);
    }

    /**
     * Update an existing order dimension.
     *
     * @param uuid the order dimension UUID
     * @param orderDimDTO the order dimension data to update
     * @return the updated order dimension
     * @throws ResourceNotFoundException if order dimension not found
     */
    @Transactional
    public OrderDimDTO update(UUID uuid, OrderDimDTO orderDimDTO) {
        return orderDimRepository.findById(uuid)
                .map(existingOrderDim -> {
                    existingOrderDim.setOrderType(orderDimDTO.getOrderType());
                    existingOrderDim.setDirectionType(orderDimDTO.getDirectionType());
                    existingOrderDim.setActionType(orderDimDTO.getActionType());
                    existingOrderDim.setAssetType(orderDimDTO.getAssetType());
                    existingOrderDim.setVolume(orderDimDTO.getVolume());
                    return convertToDTO(orderDimRepository.save(existingOrderDim));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Order dimension not found with id: " + uuid));
    }

    /**
     * Delete an order dimension by UUID.
     *
     * @param uuid the order dimension UUID
     * @throws ResourceNotFoundException if order dimension not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!orderDimRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Order dimension not found with id: " + uuid);
        }
        orderDimRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param orderDim the entity
     * @return the DTO
     */
    private OrderDimDTO convertToDTO(OrderDim orderDim) {
        OrderDimDTO dto = new OrderDimDTO();
        dto.setOrderDimUuid(orderDim.getOrderDimUuid());
        dto.setOrderType(orderDim.getOrderType());
        dto.setDirectionType(orderDim.getDirectionType());
        dto.setActionType(orderDim.getActionType());
        dto.setAssetType(orderDim.getAssetType());
        dto.setVolume(orderDim.getVolume());
        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param orderDimDTO the DTO
     * @return the entity
     */
    private OrderDim convertToEntity(OrderDimDTO orderDimDTO) {
        OrderDim entity = new OrderDim();
        entity.setOrderDimUuid(orderDimDTO.getOrderDimUuid());
        entity.setOrderType(orderDimDTO.getOrderType());
        entity.setDirectionType(orderDimDTO.getDirectionType());
        entity.setActionType(orderDimDTO.getActionType());
        entity.setAssetType(orderDimDTO.getAssetType());
        entity.setVolume(orderDimDTO.getVolume());
        return entity;
    }
}
