package com.trading.api.controller;

import com.trading.api.dto.RiskManagementFactDTO;
import com.trading.api.service.RiskManagementFactService;
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
 * REST controller for managing RiskManagementFact entities.
 */
@RestController
@RequestMapping("/api/risk-management")
@RequiredArgsConstructor
@Tag(name = "Risk Management", description = "Risk management APIs")
public class RiskManagementFactController {

    private final RiskManagementFactService riskManagementFactService;

    /**
     * GET /api/risk-management : Get all risk management facts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of risk management facts in body
     */
    @GetMapping
    @Operation(summary = "Get all risk management facts", description = "Returns all risk management facts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved risk management facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RiskManagementFactDTO.class)))
    })
    public ResponseEntity<List<RiskManagementFactDTO>> getAllRiskManagementFacts() {
        List<RiskManagementFactDTO> riskManagementFacts = riskManagementFactService.findAll();
        return ResponseEntity.ok(riskManagementFacts);
    }

    /**
     * GET /api/risk-management/{uuid} : Get risk management fact by UUID.
     *
     * @param uuid the UUID of the risk management fact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the risk management fact, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get risk management fact by UUID", description = "Returns a risk management fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved risk management fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RiskManagementFactDTO.class))),
            @ApiResponse(responseCode = "404", description = "Risk management fact not found")
    })
    public ResponseEntity<RiskManagementFactDTO> getRiskManagementFactByUuid(
            @Parameter(description = "UUID of the risk management fact to be obtained", required = true)
            @PathVariable UUID uuid) {
        RiskManagementFactDTO riskManagementFact = riskManagementFactService.findById(uuid);
        return ResponseEntity.ok(riskManagementFact);
    }

    /**
     * GET /api/risk-management/trade-zero/{tradeZeroFactUuid} : Get risk management facts by trade zero fact UUID.
     *
     * @param tradeZeroFactUuid the trade zero fact UUID of the risk management facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of risk management facts in body
     */
    @GetMapping("/trade-zero/{tradeZeroFactUuid}")
    @Operation(summary = "Get risk management facts by trade zero fact UUID", description = "Returns risk management facts by trade zero fact UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved risk management facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RiskManagementFactDTO.class)))
    })
    public ResponseEntity<List<RiskManagementFactDTO>> getRiskManagementFactsByTradeZeroFactUuid(
            @Parameter(description = "Trade zero fact UUID of the risk management facts to be obtained", required = true)
            @PathVariable UUID tradeZeroFactUuid) {
        List<RiskManagementFactDTO> riskManagementFacts = riskManagementFactService.findByTradeZeroFactUuid(tradeZeroFactUuid);
        return ResponseEntity.ok(riskManagementFacts);
    }

    /**
     * GET /api/risk-management/actions/{actions} : Get risk management facts by actions count.
     *
     * @param actions the actions count of the risk management facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of risk management facts in body
     */
    @GetMapping("/actions/{actions}")
    @Operation(summary = "Get risk management facts by actions count", description = "Returns risk management facts by actions count")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved risk management facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RiskManagementFactDTO.class)))
    })
    public ResponseEntity<List<RiskManagementFactDTO>> getRiskManagementFactsByActions(
            @Parameter(description = "Actions count of the risk management facts to be obtained", required = true)
            @PathVariable Integer actions) {
        List<RiskManagementFactDTO> riskManagementFacts = riskManagementFactService.findByActions(actions);
        return ResponseEntity.ok(riskManagementFacts);
    }

    /**
     * POST /api/risk-management : Create a new risk management fact.
     *
     * @param riskManagementFactDTO the risk management fact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new risk management fact
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new risk management fact", description = "Creates a new risk management fact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created risk management fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RiskManagementFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<RiskManagementFactDTO> createRiskManagementFact(
            @Parameter(description = "Risk management fact to be created", required = true)
            @Valid @RequestBody RiskManagementFactDTO riskManagementFactDTO) {
        RiskManagementFactDTO createdRiskManagementFact = riskManagementFactService.create(riskManagementFactDTO);
        return new ResponseEntity<>(createdRiskManagementFact, HttpStatus.CREATED);
    }

    /**
     * PUT /api/risk-management/{uuid} : Update an existing risk management fact.
     *
     * @param uuid the UUID of the risk management fact to update
     * @param riskManagementFactDTO the risk management fact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated risk management fact,
     * or with status 404 (Not Found) if the risk management fact is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing risk management fact", description = "Updates an existing risk management fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated risk management fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RiskManagementFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Risk management fact not found")
    })
    public ResponseEntity<RiskManagementFactDTO> updateRiskManagementFact(
            @Parameter(description = "UUID of the risk management fact to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated risk management fact information", required = true)
            @Valid @RequestBody RiskManagementFactDTO riskManagementFactDTO) {
        RiskManagementFactDTO updatedRiskManagementFact = riskManagementFactService.update(uuid, riskManagementFactDTO);
        return ResponseEntity.ok(updatedRiskManagementFact);
    }

    /**
     * DELETE /api/risk-management/{uuid} : Delete a risk management fact.
     *
     * @param uuid the UUID of the risk management fact to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a risk management fact", description = "Deletes a risk management fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted risk management fact"),
            @ApiResponse(responseCode = "404", description = "Risk management fact not found")
    })
    public ResponseEntity<Void> deleteRiskManagementFact(
            @Parameter(description = "UUID of the risk management fact to be deleted", required = true)
            @PathVariable UUID uuid) {
        riskManagementFactService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
