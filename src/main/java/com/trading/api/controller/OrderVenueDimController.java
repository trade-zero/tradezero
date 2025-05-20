package com.trading.api.controller;

import com.trading.api.dto.OrderVenueDimDTO;
import com.trading.api.service.OrderVenueDimService;
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
 * REST controller for managing OrderVenueDim entities.
 */
@RestController
@RequestMapping("/api/order-venues")
@RequiredArgsConstructor
@Tag(name = "Order Venue", description = "Order venue management APIs")
public class OrderVenueDimController {

    private final OrderVenueDimService orderVenueDimService;

    /**
     * GET /api/order-venues : Get all order venues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of order venues in body
     */
    @GetMapping
    @Operation(summary = "Get all order venues", description = "Returns all order venues")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order venues",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderVenueDimDTO.class)))
    })
    public ResponseEntity<List<OrderVenueDimDTO>> getAllOrderVenues() {
        List<OrderVenueDimDTO> orderVenues = orderVenueDimService.findAll();
        return ResponseEntity.ok(orderVenues);
    }

    /**
     * GET /api/order-venues/{uuid} : Get order venue by UUID.
     *
     * @param uuid the UUID of the order venue to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the order venue, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get order venue by UUID", description = "Returns an order venue by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order venue",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderVenueDimDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order venue not found")
    })
    public ResponseEntity<OrderVenueDimDTO> getOrderVenueByUuid(
            @Parameter(description = "UUID of the order venue to be obtained", required = true)
            @PathVariable UUID uuid) {
        OrderVenueDimDTO orderVenue = orderVenueDimService.findById(uuid);
        return ResponseEntity.ok(orderVenue);
    }

    /**
     * GET /api/order-venues/exchange/{exchange} : Get order venues by exchange.
     *
     * @param exchange the exchange of the order venues to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of order venues in body
     */
    @GetMapping("/exchange/{exchange}")
    @Operation(summary = "Get order venues by exchange", description = "Returns order venues by exchange")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order venues",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderVenueDimDTO.class)))
    })
    public ResponseEntity<List<OrderVenueDimDTO>> getOrderVenuesByExchange(
            @Parameter(description = "Exchange of the order venues to be obtained", required = true)
            @PathVariable String exchange) {
        List<OrderVenueDimDTO> orderVenues = orderVenueDimService.findByExchange(exchange);
        return ResponseEntity.ok(orderVenues);
    }

    /**
     * GET /api/order-venues/broker/{broker} : Get order venues by broker.
     *
     * @param broker the broker of the order venues to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of order venues in body
     */
    @GetMapping("/broker/{broker}")
    @Operation(summary = "Get order venues by broker", description = "Returns order venues by broker")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order venues",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderVenueDimDTO.class)))
    })
    public ResponseEntity<List<OrderVenueDimDTO>> getOrderVenuesByBroker(
            @Parameter(description = "Broker of the order venues to be obtained", required = true)
            @PathVariable String broker) {
        List<OrderVenueDimDTO> orderVenues = orderVenueDimService.findByBroker(broker);
        return ResponseEntity.ok(orderVenues);
    }

    /**
     * GET /api/order-venues/platform/{platform} : Get order venues by platform.
     *
     * @param platform the platform of the order venues to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of order venues in body
     */
    @GetMapping("/platform/{platform}")
    @Operation(summary = "Get order venues by platform", description = "Returns order venues by platform")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order venues",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderVenueDimDTO.class)))
    })
    public ResponseEntity<List<OrderVenueDimDTO>> getOrderVenuesByPlatform(
            @Parameter(description = "Platform of the order venues to be obtained", required = true)
            @PathVariable String platform) {
        List<OrderVenueDimDTO> orderVenues = orderVenueDimService.findByPlatform(platform);
        return ResponseEntity.ok(orderVenues);
    }

    /**
     * POST /api/order-venues : Create a new order venue.
     *
     * @param orderVenueDimDTO the order venue to create
     * @return the ResponseEntity with status 201 (Created) and with body the new order venue
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new order venue", description = "Creates a new order venue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created order venue",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderVenueDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<OrderVenueDimDTO> createOrderVenue(
            @Parameter(description = "Order venue to be created", required = true)
            @Valid @RequestBody OrderVenueDimDTO orderVenueDimDTO) {
        OrderVenueDimDTO createdOrderVenue = orderVenueDimService.create(orderVenueDimDTO);
        return new ResponseEntity<>(createdOrderVenue, HttpStatus.CREATED);
    }

    /**
     * PUT /api/order-venues/{uuid} : Update an existing order venue.
     *
     * @param uuid the UUID of the order venue to update
     * @param orderVenueDimDTO the order venue to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated order venue,
     * or with status 404 (Not Found) if the order venue is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing order venue", description = "Updates an existing order venue by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated order venue",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderVenueDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Order venue not found")
    })
    public ResponseEntity<OrderVenueDimDTO> updateOrderVenue(
            @Parameter(description = "UUID of the order venue to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated order venue information", required = true)
            @Valid @RequestBody OrderVenueDimDTO orderVenueDimDTO) {
        OrderVenueDimDTO updatedOrderVenue = orderVenueDimService.update(uuid, orderVenueDimDTO);
        return ResponseEntity.ok(updatedOrderVenue);
    }

    /**
     * DELETE /api/order-venues/{uuid} : Delete an order venue.
     *
     * @param uuid the UUID of the order venue to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an order venue", description = "Deletes an order venue by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted order venue"),
            @ApiResponse(responseCode = "404", description = "Order venue not found")
    })
    public ResponseEntity<Void> deleteOrderVenue(
            @Parameter(description = "UUID of the order venue to be deleted", required = true)
            @PathVariable UUID uuid) {
        orderVenueDimService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
