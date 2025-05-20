package com.trading.api.controller;

import com.trading.api.dto.OrderFactDTO;
import com.trading.api.model.enums.OrderStatusType;
import com.trading.api.service.OrderFactService;
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
 * REST controller for managing OrderFact entities.
 */
@RestController
@RequestMapping("/api/order-facts")
@RequiredArgsConstructor
@Tag(name = "Order Fact", description = "Order fact management APIs")
public class OrderFactController {

    private final OrderFactService orderFactService;

    /**
     * GET /api/order-facts : Get all order facts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of order facts in body
     */
    @GetMapping
    @Operation(summary = "Get all order facts", description = "Returns all order facts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderFactDTO.class)))
    })
    public ResponseEntity<List<OrderFactDTO>> getAllOrderFacts() {
        List<OrderFactDTO> orderFacts = orderFactService.findAll();
        return ResponseEntity.ok(orderFacts);
    }

    /**
     * GET /api/order-facts/{uuid} : Get order fact by UUID.
     *
     * @param uuid the UUID of the order fact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the order fact, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get order fact by UUID", description = "Returns an order fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderFactDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order fact not found")
    })
    public ResponseEntity<OrderFactDTO> getOrderFactByUuid(
            @Parameter(description = "UUID of the order fact to be obtained", required = true)
            @PathVariable UUID uuid) {
        OrderFactDTO orderFact = orderFactService.findById(uuid);
        return ResponseEntity.ok(orderFact);
    }

    /**
     * GET /api/order-facts/order-dimension/{orderDimUuid} : Get order facts by order dimension UUID.
     *
     * @param orderDimUuid the order dimension UUID of the order facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of order facts in body
     */
    @GetMapping("/order-dimension/{orderDimUuid}")
    @Operation(summary = "Get order facts by order dimension UUID", description = "Returns order facts by order dimension UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderFactDTO.class)))
    })
    public ResponseEntity<List<OrderFactDTO>> getOrderFactsByOrderDimUuid(
            @Parameter(description = "Order dimension UUID of the order facts to be obtained", required = true)
            @PathVariable UUID orderDimUuid) {
        List<OrderFactDTO> orderFacts = orderFactService.findByOrderDimUuid(orderDimUuid);
        return ResponseEntity.ok(orderFacts);
    }

    /**
     * GET /api/order-facts/order-venue/{orderVenueDimUuid} : Get order facts by order venue dimension UUID.
     *
     * @param orderVenueDimUuid the order venue dimension UUID of the order facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of order facts in body
     */
    @GetMapping("/order-venue/{orderVenueDimUuid}")
    @Operation(summary = "Get order facts by order venue dimension UUID", description = "Returns order facts by order venue dimension UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderFactDTO.class)))
    })
    public ResponseEntity<List<OrderFactDTO>> getOrderFactsByOrderVenueDimUuid(
            @Parameter(description = "Order venue dimension UUID of the order facts to be obtained", required = true)
            @PathVariable UUID orderVenueDimUuid) {
        List<OrderFactDTO> orderFacts = orderFactService.findByOrderVenueDimUuid(orderVenueDimUuid);
        return ResponseEntity.ok(orderFacts);
    }

    /**
     * GET /api/order-facts/portfolio/{portfolioUuid} : Get order facts by portfolio UUID.
     *
     * @param portfolioUuid the portfolio UUID of the order facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of order facts in body
     */
    @GetMapping("/portfolio/{portfolioUuid}")
    @Operation(summary = "Get order facts by portfolio UUID", description = "Returns order facts by portfolio UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderFactDTO.class)))
    })
    public ResponseEntity<List<OrderFactDTO>> getOrderFactsByPortfolioUuid(
            @Parameter(description = "Portfolio UUID of the order facts to be obtained", required = true)
            @PathVariable UUID portfolioUuid) {
        List<OrderFactDTO> orderFacts = orderFactService.findByPortfolioUuid(portfolioUuid);
        return ResponseEntity.ok(orderFacts);
    }

    /**
     * GET /api/order-facts/status/{status} : Get order facts by status.
     *
     * @param status the status of the order facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of order facts in body
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get order facts by status", description = "Returns order facts by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderFactDTO.class)))
    })
    public ResponseEntity<List<OrderFactDTO>> getOrderFactsByStatus(
            @Parameter(description = "Status of the order facts to be obtained", required = true)
            @PathVariable OrderStatusType status) {
        List<OrderFactDTO> orderFacts = orderFactService.findByOrderStatus(status);
        return ResponseEntity.ok(orderFacts);
    }

    /**
     * POST /api/order-facts : Create a new order fact.
     *
     * @param orderFactDTO the order fact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new order fact
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new order fact", description = "Creates a new order fact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created order fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<OrderFactDTO> createOrderFact(
            @Parameter(description = "Order fact to be created", required = true)
            @Valid @RequestBody OrderFactDTO orderFactDTO) {
        OrderFactDTO createdOrderFact = orderFactService.create(orderFactDTO);
        return new ResponseEntity<>(createdOrderFact, HttpStatus.CREATED);
    }

    /**
     * PUT /api/order-facts/{uuid} : Update an existing order fact.
     *
     * @param uuid the UUID of the order fact to update
     * @param orderFactDTO the order fact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated order fact,
     * or with status 404 (Not Found) if the order fact is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing order fact", description = "Updates an existing order fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated order fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Order fact not found")
    })
    public ResponseEntity<OrderFactDTO> updateOrderFact(
            @Parameter(description = "UUID of the order fact to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated order fact information", required = true)
            @Valid @RequestBody OrderFactDTO orderFactDTO) {
        OrderFactDTO updatedOrderFact = orderFactService.update(uuid, orderFactDTO);
        return ResponseEntity.ok(updatedOrderFact);
    }

    /**
     * DELETE /api/order-facts/{uuid} : Delete an order fact.
     *
     * @param uuid the UUID of the order fact to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an order fact", description = "Deletes an order fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted order fact"),
            @ApiResponse(responseCode = "404", description = "Order fact not found")
    })
    public ResponseEntity<Void> deleteOrderFact(
            @Parameter(description = "UUID of the order fact to be deleted", required = true)
            @PathVariable UUID uuid) {
        orderFactService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
