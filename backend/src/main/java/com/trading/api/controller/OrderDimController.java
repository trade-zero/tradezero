package com.trading.api.controller;

import com.trading.api.dto.OrderDimDTO;
import com.trading.api.model.enums.OrderType;
import com.trading.api.model.enums.TradeActionType;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import com.trading.api.service.OrderDimService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing OrderDim entities.
 */
@RestController
@RequestMapping("/api/order-dimensions")
@RequiredArgsConstructor
@Tag(name = "Order Dimension", description = "Order dimension management APIs")
public class OrderDimController {

    private final OrderDimService orderDimService;

    /**
     * GET /api/order-dimensions : Get all order dimensions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of order dimensions in body
     */
    @GetMapping
    @Operation(summary = "Get all order dimensions", description = "Returns all order dimensions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order dimensions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDimDTO.class)))
    })
    public ResponseEntity<List<OrderDimDTO>> getAllOrderDimensions() {
        List<OrderDimDTO> orderDimensions = orderDimService.findAll();
        return ResponseEntity.ok(orderDimensions);
    }

    /**
     * GET /api/order-dimensions/{uuid} : Get order dimension by UUID.
     *
     * @param uuid the UUID of the order dimension to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the order dimension, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get order dimension by UUID", description = "Returns an order dimension by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order dimension",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDimDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order dimension not found")
    })
    public ResponseEntity<OrderDimDTO> getOrderDimensionByUuid(
            @Parameter(description = "UUID of the order dimension to be obtained", required = true)
            @PathVariable UUID uuid) {
        OrderDimDTO orderDimension = orderDimService.findById(uuid);
        return ResponseEntity.ok(orderDimension);
    }

    /**
     * GET /api/order-dimensions/type/{orderType} : Get order dimensions by order type.
     *
     * @param orderType the order type of the order dimensions to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of order dimensions in body
     */
    @GetMapping("/type/{orderType}")
    @Operation(summary = "Get order dimensions by order type", description = "Returns order dimensions by order type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order dimensions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDimDTO.class)))
    })
    public ResponseEntity<List<OrderDimDTO>> getOrderDimensionsByOrderType(
            @Parameter(description = "Order type of the order dimensions to be obtained", required = true)
            @PathVariable OrderType orderType) {
        List<OrderDimDTO> orderDimensions = orderDimService.findByOrderType(orderType);
        return ResponseEntity.ok(orderDimensions);
    }

    /**
     * GET /api/order-dimensions/direction/{directionType} : Get order dimensions by direction type.
     *
     * @param directionType the direction type of the order dimensions to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of order dimensions in body
     */
    @GetMapping("/direction/{directionType}")
    @Operation(summary = "Get order dimensions by direction type", description = "Returns order dimensions by direction type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order dimensions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDimDTO.class)))
    })
    public ResponseEntity<List<OrderDimDTO>> getOrderDimensionsByDirectionType(
            @Parameter(description = "Direction type of the order dimensions to be obtained", required = true)
            @PathVariable TradeDirectionType directionType) {
        List<OrderDimDTO> orderDimensions = orderDimService.findByDirectionType(directionType);
        return ResponseEntity.ok(orderDimensions);
    }

    /**
     * GET /api/order-dimensions/action/{actionType} : Get order dimensions by action type.
     *
     * @param actionType the action type of the order dimensions to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of order dimensions in body
     */
    @GetMapping("/action/{actionType}")
    @Operation(summary = "Get order dimensions by action type", description = "Returns order dimensions by action type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order dimensions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDimDTO.class)))
    })
    public ResponseEntity<List<OrderDimDTO>> getOrderDimensionsByActionType(
            @Parameter(description = "Action type of the order dimensions to be obtained", required = true)
            @PathVariable TradeActionType actionType) {
        List<OrderDimDTO> orderDimensions = orderDimService.findByActionType(actionType);
        return ResponseEntity.ok(orderDimensions);
    }

    /**
     * GET /api/order-dimensions/asset/{assetType} : Get order dimensions by asset type.
     *
     * @param assetType the asset type of the order dimensions to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of order dimensions in body
     */
    @GetMapping("/asset/{assetType}")
    @Operation(summary = "Get order dimensions by asset type", description = "Returns order dimensions by asset type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order dimensions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDimDTO.class)))
    })
    public ResponseEntity<List<OrderDimDTO>> getOrderDimensionsByAssetType(
            @Parameter(description = "Asset type of the order dimensions to be obtained", required = true)
            @PathVariable TradeAssetType assetType) {
        List<OrderDimDTO> orderDimensions = orderDimService.findByAssetType(assetType);
        return ResponseEntity.ok(orderDimensions);
    }

    /**
     * POST /api/order-dimensions : Create a new order dimension.
     *
     * @param orderDimDTO the order dimension to create
     * @return the ResponseEntity with status 201 (Created) and with body the new order dimension
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new order dimension", description = "Creates a new order dimension")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created order dimension",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<OrderDimDTO> createOrderDimension(
            @Parameter(description = "Order dimension to be created", required = true)
            @Valid @RequestBody OrderDimDTO orderDimDTO) {
        OrderDimDTO createdOrderDimension = orderDimService.create(orderDimDTO);
        return new ResponseEntity<>(createdOrderDimension, HttpStatus.CREATED);
    }

    /**
     * PUT /api/order-dimensions/{uuid} : Update an existing order dimension.
     *
     * @param uuid the UUID of the order dimension to update
     * @param orderDimDTO the order dimension to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated order dimension,
     * or with status 404 (Not Found) if the order dimension is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing order dimension", description = "Updates an existing order dimension by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated order dimension",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Order dimension not found")
    })
    public ResponseEntity<OrderDimDTO> updateOrderDimension(
            @Parameter(description = "UUID of the order dimension to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated order dimension information", required = true)
            @Valid @RequestBody OrderDimDTO orderDimDTO) {
        OrderDimDTO updatedOrderDimension = orderDimService.update(uuid, orderDimDTO);
        return ResponseEntity.ok(updatedOrderDimension);
    }

    /**
     * DELETE /api/order-dimensions/{uuid} : Delete an order dimension.
     *
     * @param uuid the UUID of the order dimension to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an order dimension", description = "Deletes an order dimension by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted order dimension"),
            @ApiResponse(responseCode = "404", description = "Order dimension not found")
    })
    public ResponseEntity<Void> deleteOrderDimension(
            @Parameter(description = "UUID of the order dimension to be deleted", required = true)
            @PathVariable UUID uuid) {
        orderDimService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
