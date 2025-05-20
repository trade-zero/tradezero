package com.trading.api.controller;

import com.trading.api.dto.TradeZeroFactDTO;
import com.trading.api.service.TradeZeroFactService;
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
 * REST controller for managing TradeZeroFact entities.
 */
@RestController
@RequestMapping("/api/tradezero-facts")
@RequiredArgsConstructor
@Tag(name = "Trade Zero Fact", description = "Trade zero fact management APIs")
public class TradeZeroFactController {

    private final TradeZeroFactService tradeZeroFactService;

    /**
     * GET /api/tradezero-facts : Get all trade zero facts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of trade zero facts in body
     */
    @GetMapping
    @Operation(summary = "Get all trade zero facts", description = "Returns all trade zero facts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trade zero facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TradeZeroFactDTO.class)))
    })
    public ResponseEntity<List<TradeZeroFactDTO>> getAllTradeZeroFacts() {
        List<TradeZeroFactDTO> tradeZeroFacts = tradeZeroFactService.findAll();
        return ResponseEntity.ok(tradeZeroFacts);
    }

    /**
     * GET /api/tradezero-facts/{uuid} : Get trade zero fact by UUID.
     *
     * @param uuid the UUID of the trade zero fact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trade zero fact, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get trade zero fact by UUID", description = "Returns a trade zero fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trade zero fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TradeZeroFactDTO.class))),
            @ApiResponse(responseCode = "404", description = "Trade zero fact not found")
    })
    public ResponseEntity<TradeZeroFactDTO> getTradeZeroFactByUuid(
            @Parameter(description = "UUID of the trade zero fact to be obtained", required = true)
            @PathVariable UUID uuid) {
        TradeZeroFactDTO tradeZeroFact = tradeZeroFactService.findById(uuid);
        return ResponseEntity.ok(tradeZeroFact);
    }

    /**
     * GET /api/tradezero-facts/dimension/{tradeZeroDimUuid} : Get trade zero facts by trade zero dimension UUID.
     *
     * @param tradeZeroDimUuid the trade zero dimension UUID of the trade zero facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of trade zero facts in body
     */
    @GetMapping("/dimension/{tradeZeroDimUuid}")
    @Operation(summary = "Get trade zero facts by trade zero dimension UUID", description = "Returns trade zero facts by trade zero dimension UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trade zero facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TradeZeroFactDTO.class)))
    })
    public ResponseEntity<List<TradeZeroFactDTO>> getTradeZeroFactsByTradeZeroDimUuid(
            @Parameter(description = "Trade zero dimension UUID of the trade zero facts to be obtained", required = true)
            @PathVariable UUID tradeZeroDimUuid) {
        List<TradeZeroFactDTO> tradeZeroFacts = tradeZeroFactService.findByTradeZeroDimUuid(tradeZeroDimUuid);
        return ResponseEntity.ok(tradeZeroFacts);
    }

    /**
     * GET /api/tradezero-facts/agent/{agentDimUuid} : Get trade zero facts by agent dimension UUID.
     *
     * @param agentDimUuid the agent dimension UUID of the trade zero facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of trade zero facts in body
     */
    @GetMapping("/agent/{agentDimUuid}")
    @Operation(summary = "Get trade zero facts by agent dimension UUID", description = "Returns trade zero facts by agent dimension UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trade zero facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TradeZeroFactDTO.class)))
    })
    public ResponseEntity<List<TradeZeroFactDTO>> getTradeZeroFactsByAgentDimUuid(
            @Parameter(description = "Agent dimension UUID of the trade zero facts to be obtained", required = true)
            @PathVariable UUID agentDimUuid) {
        List<TradeZeroFactDTO> tradeZeroFacts = tradeZeroFactService.findByAgentDimUuid(agentDimUuid);
        return ResponseEntity.ok(tradeZeroFacts);
    }

    /**
     * POST /api/tradezero-facts : Create a new trade zero fact.
     *
     * @param tradeZeroFactDTO the trade zero fact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trade zero fact
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new trade zero fact", description = "Creates a new trade zero fact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created trade zero fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TradeZeroFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<TradeZeroFactDTO> createTradeZeroFact(
            @Parameter(description = "Trade zero fact to be created", required = true)
            @Valid @RequestBody TradeZeroFactDTO tradeZeroFactDTO) {
        TradeZeroFactDTO createdTradeZeroFact = tradeZeroFactService.create(tradeZeroFactDTO);
        return new ResponseEntity<>(createdTradeZeroFact, HttpStatus.CREATED);
    }

    /**
     * PUT /api/tradezero-facts/{uuid} : Update an existing trade zero fact.
     *
     * @param uuid the UUID of the trade zero fact to update
     * @param tradeZeroFactDTO the trade zero fact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trade zero fact,
     * or with status 404 (Not Found) if the trade zero fact is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing trade zero fact", description = "Updates an existing trade zero fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated trade zero fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TradeZeroFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Trade zero fact not found")
    })
    public ResponseEntity<TradeZeroFactDTO> updateTradeZeroFact(
            @Parameter(description = "UUID of the trade zero fact to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated trade zero fact information", required = true)
            @Valid @RequestBody TradeZeroFactDTO tradeZeroFactDTO) {
        TradeZeroFactDTO updatedTradeZeroFact = tradeZeroFactService.update(uuid, tradeZeroFactDTO);
        return ResponseEntity.ok(updatedTradeZeroFact);
    }

    /**
     * DELETE /api/tradezero-facts/{uuid} : Delete a trade zero fact.
     *
     * @param uuid the UUID of the trade zero fact to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a trade zero fact", description = "Deletes a trade zero fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted trade zero fact"),
            @ApiResponse(responseCode = "404", description = "Trade zero fact not found")
    })
    public ResponseEntity<Void> deleteTradeZeroFact(
            @Parameter(description = "UUID of the trade zero fact to be deleted", required = true)
            @PathVariable UUID uuid) {
        tradeZeroFactService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
