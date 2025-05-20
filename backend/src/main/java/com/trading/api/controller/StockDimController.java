package com.trading.api.controller;

import com.trading.api.dto.StockDimDTO;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.service.StockDimService;
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
 * REST controller for managing StockDim entities.
 */
@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@Tag(name = "Stock", description = "Stock management APIs")
public class StockDimController {

    private final StockDimService stockDimService;

    /**
     * GET /api/stocks : Get all stocks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stocks in body
     */
    @GetMapping
    @Operation(summary = "Get all stocks", description = "Returns all stocks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved stocks",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockDimDTO.class)))
    })
    public ResponseEntity<List<StockDimDTO>> getAllStocks() {
        List<StockDimDTO> stocks = stockDimService.findAll();
        return ResponseEntity.ok(stocks);
    }

    /**
     * GET /api/stocks/{uuid} : Get stock by UUID.
     *
     * @param uuid the UUID of the stock to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stock, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get stock by UUID", description = "Returns a stock by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved stock",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockDimDTO.class))),
            @ApiResponse(responseCode = "404", description = "Stock not found")
    })
    public ResponseEntity<StockDimDTO> getStockByUuid(
            @Parameter(description = "UUID of the stock to be obtained", required = true)
            @PathVariable UUID uuid) {
        StockDimDTO stock = stockDimService.findById(uuid);
        return ResponseEntity.ok(stock);
    }

    /**
     * GET /api/stocks/asset-type/{assetType} : Get stock by asset type.
     *
     * @param assetType the asset type of the stock to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stock, or with status 404 (Not Found)
     */
    @GetMapping("/asset-type/{assetType}")
    @Operation(summary = "Get stock by asset type", description = "Returns a stock by asset type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved stock",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockDimDTO.class))),
            @ApiResponse(responseCode = "404", description = "Stock not found")
    })
    public ResponseEntity<StockDimDTO> getStockByAssetType(
            @Parameter(description = "Asset type of the stock to be obtained", required = true)
            @PathVariable TradeAssetType assetType) {
        StockDimDTO stock = stockDimService.findByAssetType(assetType);
        return ResponseEntity.ok(stock);
    }

    /**
     * POST /api/stocks : Create a new stock.
     *
     * @param stockDimDTO the stock to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stock
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new stock", description = "Creates a new stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created stock",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<StockDimDTO> createStock(
            @Parameter(description = "Stock to be created", required = true)
            @Valid @RequestBody StockDimDTO stockDimDTO) {
        StockDimDTO createdStock = stockDimService.create(stockDimDTO);
        return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
    }

    /**
     * PUT /api/stocks/{uuid} : Update an existing stock.
     *
     * @param uuid the UUID of the stock to update
     * @param stockDimDTO the stock to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stock,
     * or with status 404 (Not Found) if the stock is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing stock", description = "Updates an existing stock by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated stock",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Stock not found")
    })
    public ResponseEntity<StockDimDTO> updateStock(
            @Parameter(description = "UUID of the stock to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated stock information", required = true)
            @Valid @RequestBody StockDimDTO stockDimDTO) {
        StockDimDTO updatedStock = stockDimService.update(uuid, stockDimDTO);
        return ResponseEntity.ok(updatedStock);
    }

    /**
     * DELETE /api/stocks/{uuid} : Delete a stock.
     *
     * @param uuid the UUID of the stock to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a stock", description = "Deletes a stock by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted stock"),
            @ApiResponse(responseCode = "404", description = "Stock not found")
    })
    public ResponseEntity<Void> deleteStock(
            @Parameter(description = "UUID of the stock to be deleted", required = true)
            @PathVariable UUID uuid) {
        stockDimService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
