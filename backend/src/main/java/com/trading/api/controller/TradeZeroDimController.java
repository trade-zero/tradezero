package com.trading.api.controller;

import com.trading.api.dto.TradeZeroDimDTO;
import com.trading.api.model.enums.TradeTimeFrameType;
import com.trading.api.service.TradeZeroDimService;
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
 * REST controller for managing TradeZeroDim entities.
 */
@RestController
@RequestMapping("/api/tradezero-dimensions")
@RequiredArgsConstructor
@Tag(name = "Trade Zero Dimension", description = "Trade zero dimension management APIs")
public class TradeZeroDimController {

    private final TradeZeroDimService tradeZeroDimService;

    /**
     * GET /api/tradezero-dimensions : Get all trade zero dimensions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of trade zero dimensions in body
     */
    @GetMapping
    @Operation(summary = "Get all trade zero dimensions", description = "Returns all trade zero dimensions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trade zero dimensions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TradeZeroDimDTO.class)))
    })
    public ResponseEntity<List<TradeZeroDimDTO>> getAllTradeZeroDimensions() {
        List<TradeZeroDimDTO> tradeZeroDimensions = tradeZeroDimService.findAll();
        return ResponseEntity.ok(tradeZeroDimensions);
    }

    /**
     * GET /api/tradezero-dimensions/{uuid} : Get trade zero dimension by UUID.
     *
     * @param uuid the UUID of the trade zero dimension to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trade zero dimension, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get trade zero dimension by UUID", description = "Returns a trade zero dimension by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trade zero dimension",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TradeZeroDimDTO.class))),
            @ApiResponse(responseCode = "404", description = "Trade zero dimension not found")
    })
    public ResponseEntity<TradeZeroDimDTO> getTradeZeroDimensionByUuid(
            @Parameter(description = "UUID of the trade zero dimension to be obtained", required = true)
            @PathVariable UUID uuid) {
        TradeZeroDimDTO tradeZeroDimension = tradeZeroDimService.findById(uuid);
        return ResponseEntity.ok(tradeZeroDimension);
    }

    /**
     * GET /api/tradezero-dimensions/timeframe/{timeFrame} : Get trade zero dimensions by time frame.
     *
     * @param timeFrame the time frame of the trade zero dimensions to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of trade zero dimensions in body
     */
    @GetMapping("/timeframe/{timeFrame}")
    @Operation(summary = "Get trade zero dimensions by time frame", description = "Returns trade zero dimensions by time frame")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trade zero dimensions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TradeZeroDimDTO.class)))
    })
    public ResponseEntity<List<TradeZeroDimDTO>> getTradeZeroDimensionsByTimeFrame(
            @Parameter(description = "Time frame of the trade zero dimensions to be obtained", required = true)
            @PathVariable TradeTimeFrameType timeFrame) {
        List<TradeZeroDimDTO> tradeZeroDimensions = tradeZeroDimService.findByTimeFrame(timeFrame);
        return ResponseEntity.ok(tradeZeroDimensions);
    }

    /**
     * POST /api/tradezero-dimensions : Create a new trade zero dimension.
     *
     * @param tradeZeroDimDTO the trade zero dimension to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trade zero dimension
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new trade zero dimension", description = "Creates a new trade zero dimension")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created trade zero dimension",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TradeZeroDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<TradeZeroDimDTO> createTradeZeroDimension(
            @Parameter(description = "Trade zero dimension to be created", required = true)
            @Valid @RequestBody TradeZeroDimDTO tradeZeroDimDTO) {
        TradeZeroDimDTO createdTradeZeroDimension = tradeZeroDimService.create(tradeZeroDimDTO);
        return new ResponseEntity<>(createdTradeZeroDimension, HttpStatus.CREATED);
    }

    /**
     * PUT /api/tradezero-dimensions/{uuid} : Update an existing trade zero dimension.
     *
     * @param uuid the UUID of the trade zero dimension to update
     * @param tradeZeroDimDTO the trade zero dimension to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trade zero dimension,
     * or with status 404 (Not Found) if the trade zero dimension is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing trade zero dimension", description = "Updates an existing trade zero dimension by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated trade zero dimension",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TradeZeroDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Trade zero dimension not found")
    })
    public ResponseEntity<TradeZeroDimDTO> updateTradeZeroDimension(
            @Parameter(description = "UUID of the trade zero dimension to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated trade zero dimension information", required = true)
            @Valid @RequestBody TradeZeroDimDTO tradeZeroDimDTO) {
        TradeZeroDimDTO updatedTradeZeroDimension = tradeZeroDimService.update(uuid, tradeZeroDimDTO);
        return ResponseEntity.ok(updatedTradeZeroDimension);
    }

    /**
     * DELETE /api/tradezero-dimensions/{uuid} : Delete a trade zero dimension.
     *
     * @param uuid the UUID of the trade zero dimension to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a trade zero dimension", description = "Deletes a trade zero dimension by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted trade zero dimension"),
            @ApiResponse(responseCode = "404", description = "Trade zero dimension not found")
    })
    public ResponseEntity<Void> deleteTradeZeroDimension(
            @Parameter(description = "UUID of the trade zero dimension to be deleted", required = true)
            @PathVariable UUID uuid) {
        tradeZeroDimService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
