package com.trading.api.controller;

import com.trading.api.dto.CandlestickFactDTO;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeTimeFrameType;
import com.trading.api.service.CandlestickFactService;
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
 * REST controller for managing CandlestickFact entities.
 */
@RestController
@RequestMapping("/api/candlesticks")
@RequiredArgsConstructor
@Tag(name = "Candlestick", description = "Candlestick management APIs")
public class CandlestickFactController {

    private final CandlestickFactService candlestickFactService;

    /**
     * GET /api/candlesticks : Get all candlesticks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candlesticks in body
     */
    @GetMapping
    @Operation(summary = "Get all candlesticks", description = "Returns all candlesticks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved candlesticks",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CandlestickFactDTO.class)))
    })
    public ResponseEntity<List<CandlestickFactDTO>> getAllCandlesticks() {
        List<CandlestickFactDTO> candlesticks = candlestickFactService.findAll();
        return ResponseEntity.ok(candlesticks);
    }

    /**
     * GET /api/candlesticks/datafeed/{dataFeedUuid} : Get candlesticks by data feed UUID.
     *
     * @param dataFeedUuid the data feed UUID
     * @return the ResponseEntity with status 200 (OK) and the list of candlesticks in body
     */
    @GetMapping("/datafeed/{dataFeedUuid}")
    @Operation(summary = "Get candlesticks by data feed UUID", description = "Returns candlesticks by data feed UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved candlesticks",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CandlestickFactDTO.class)))
    })
    public ResponseEntity<List<CandlestickFactDTO>> getCandlesticksByDataFeedUuid(
            @Parameter(description = "Data feed UUID of the candlesticks to be obtained", required = true)
            @PathVariable UUID dataFeedUuid) {
        List<CandlestickFactDTO> candlesticks = candlestickFactService.findByDataFeedUuid(dataFeedUuid);
        return ResponseEntity.ok(candlesticks);
    }

    /**
     * GET /api/candlesticks/asset/{tradeAsset} : Get candlesticks by trade asset.
     *
     * @param tradeAsset the trade asset
     * @return the ResponseEntity with status 200 (OK) and the list of candlesticks in body
     */
    @GetMapping("/asset/{tradeAsset}")
    @Operation(summary = "Get candlesticks by trade asset", description = "Returns candlesticks by trade asset")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved candlesticks",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CandlestickFactDTO.class)))
    })
    public ResponseEntity<List<CandlestickFactDTO>> getCandlesticksByTradeAsset(
            @Parameter(description = "Trade asset of the candlesticks to be obtained", required = true)
            @PathVariable TradeAssetType tradeAsset) {
        List<CandlestickFactDTO> candlesticks = candlestickFactService.findByTradeAsset(tradeAsset);
        return ResponseEntity.ok(candlesticks);
    }

    /**
     * GET /api/candlesticks/timeframe/{tradeTimeFrame} : Get candlesticks by trade time frame.
     *
     * @param tradeTimeFrame the trade time frame
     * @return the ResponseEntity with status 200 (OK) and the list of candlesticks in body
     */
    @GetMapping("/timeframe/{tradeTimeFrame}")
    @Operation(summary = "Get candlesticks by trade time frame", description = "Returns candlesticks by trade time frame")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved candlesticks",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CandlestickFactDTO.class)))
    })
    public ResponseEntity<List<CandlestickFactDTO>> getCandlesticksByTradeTimeFrame(
            @Parameter(description = "Trade time frame of the candlesticks to be obtained", required = true)
            @PathVariable TradeTimeFrameType tradeTimeFrame) {
        List<CandlestickFactDTO> candlesticks = candlestickFactService.findByTradeTimeFrame(tradeTimeFrame);
        return ResponseEntity.ok(candlesticks);
    }

    /**
     * GET /api/candlesticks/datetime/{datetimeId} : Get candlesticks by datetime ID.
     *
     * @param datetimeId the datetime ID
     * @return the ResponseEntity with status 200 (OK) and the list of candlesticks in body
     */
    @GetMapping("/datetime/{datetimeId}")
    @Operation(summary = "Get candlesticks by datetime ID", description = "Returns candlesticks by datetime ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved candlesticks",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CandlestickFactDTO.class)))
    })
    public ResponseEntity<List<CandlestickFactDTO>> getCandlesticksByDatetimeId(
            @Parameter(description = "Datetime ID of the candlesticks to be obtained", required = true)
            @PathVariable Long datetimeId) {
        List<CandlestickFactDTO> candlesticks = candlestickFactService.findByDatetimeId(datetimeId);
        return ResponseEntity.ok(candlesticks);
    }

    /**
     * GET /api/candlesticks/search : Get candlesticks by data feed UUID, trade asset, and trade time frame.
     *
     * @param dataFeedUuid the data feed UUID
     * @param tradeAsset the trade asset
     * @param tradeTimeFrame the trade time frame
     * @return the ResponseEntity with status 200 (OK) and the list of candlesticks in body
     */
    @GetMapping("/search")
    @Operation(summary = "Get candlesticks by data feed UUID, trade asset, and trade time frame", 
               description = "Returns candlesticks by data feed UUID, trade asset, and trade time frame")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved candlesticks",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CandlestickFactDTO.class)))
    })
    public ResponseEntity<List<CandlestickFactDTO>> getCandlesticksByDataFeedUuidAndTradeAssetAndTradeTimeFrame(
            @Parameter(description = "Data feed UUID of the candlesticks to be obtained", required = true)
            @RequestParam UUID dataFeedUuid,
            @Parameter(description = "Trade asset of the candlesticks to be obtained", required = true)
            @RequestParam TradeAssetType tradeAsset,
            @Parameter(description = "Trade time frame of the candlesticks to be obtained", required = true)
            @RequestParam TradeTimeFrameType tradeTimeFrame) {
        List<CandlestickFactDTO> candlesticks = candlestickFactService.findByDataFeedUuidAndTradeAssetAndTradeTimeFrame(
                dataFeedUuid, tradeAsset, tradeTimeFrame);
        return ResponseEntity.ok(candlesticks);
    }

    /**
     * POST /api/candlesticks : Create a new candlestick.
     *
     * @param candlestickFactDTO the candlestick to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candlestick
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new candlestick", description = "Creates a new candlestick")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created candlestick",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CandlestickFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<CandlestickFactDTO> createCandlestick(
            @Parameter(description = "Candlestick to be created", required = true)
            @Valid @RequestBody CandlestickFactDTO candlestickFactDTO) {
        CandlestickFactDTO createdCandlestick = candlestickFactService.create(candlestickFactDTO);
        return new ResponseEntity<>(createdCandlestick, HttpStatus.CREATED);
    }

    /**
     * PUT /api/candlesticks : Update an existing candlestick.
     *
     * @param dataFeedUuid the data feed UUID
     * @param tradeAsset the trade asset
     * @param tradeTimeFrame the trade time frame
     * @param datetimeId the datetime ID
     * @param candlestickFactDTO the candlestick to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candlestick,
     * or with status 404 (Not Found) if the candlestick is not found
     */
    @PutMapping
    @Operation(summary = "Update an existing candlestick", description = "Updates an existing candlestick by composite key")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated candlestick",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CandlestickFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Candlestick not found")
    })
    public ResponseEntity<CandlestickFactDTO> updateCandlestick(
            @Parameter(description = "Data feed UUID of the candlestick to be updated", required = true)
            @RequestParam UUID dataFeedUuid,
            @Parameter(description = "Trade asset of the candlestick to be updated", required = true)
            @RequestParam TradeAssetType tradeAsset,
            @Parameter(description = "Trade time frame of the candlestick to be updated", required = true)
            @RequestParam TradeTimeFrameType tradeTimeFrame,
            @Parameter(description = "Datetime ID of the candlestick to be updated", required = true)
            @RequestParam Long datetimeId,
            @Parameter(description = "Updated candlestick information", required = true)
            @Valid @RequestBody CandlestickFactDTO candlestickFactDTO) {
        CandlestickFactDTO updatedCandlestick = candlestickFactService.update(
                dataFeedUuid, tradeAsset, tradeTimeFrame, datetimeId, candlestickFactDTO);
        return ResponseEntity.ok(updatedCandlestick);
    }

    /**
     * DELETE /api/candlesticks : Delete a candlestick.
     *
     * @param dataFeedUuid the data feed UUID
     * @param tradeAsset the trade asset
     * @param tradeTimeFrame the trade time frame
     * @param datetimeId the datetime ID
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a candlestick", description = "Deletes a candlestick by composite key")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted candlestick"),
            @ApiResponse(responseCode = "404", description = "Candlestick not found")
    })
    public ResponseEntity<Void> deleteCandlestick(
            @Parameter(description = "Data feed UUID of the candlestick to be deleted", required = true)
            @RequestParam UUID dataFeedUuid,
            @Parameter(description = "Trade asset of the candlestick to be deleted", required = true)
            @RequestParam TradeAssetType tradeAsset,
            @Parameter(description = "Trade time frame of the candlestick to be deleted", required = true)
            @RequestParam TradeTimeFrameType tradeTimeFrame,
            @Parameter(description = "Datetime ID of the candlestick to be deleted", required = true)
            @RequestParam Long datetimeId) {
        candlestickFactService.delete(dataFeedUuid, tradeAsset, tradeTimeFrame, datetimeId);
        return ResponseEntity.noContent().build();
    }
}
