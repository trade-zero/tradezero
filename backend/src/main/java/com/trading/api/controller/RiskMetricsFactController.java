package com.trading.api.controller;

import com.trading.api.dto.RiskMetricsFactDTO;
import com.trading.api.service.RiskMetricsFactService;
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
 * REST controller for managing RiskMetricsFact entities.
 */
@RestController
@RequestMapping("/api/risk-metrics")
@RequiredArgsConstructor
@Tag(name = "Risk Metrics", description = "Risk metrics management APIs")
public class RiskMetricsFactController {

    private final RiskMetricsFactService riskMetricsFactService;

    /**
     * GET /api/risk-metrics : Get all risk metrics facts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of risk metrics facts in body
     */
    @GetMapping
    @Operation(summary = "Get all risk metrics facts", description = "Returns all risk metrics facts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved risk metrics facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RiskMetricsFactDTO.class)))
    })
    public ResponseEntity<List<RiskMetricsFactDTO>> getAllRiskMetricsFacts() {
        List<RiskMetricsFactDTO> riskMetricsFacts = riskMetricsFactService.findAll();
        return ResponseEntity.ok(riskMetricsFacts);
    }

    /**
     * GET /api/risk-metrics/{uuid} : Get risk metrics fact by UUID.
     *
     * @param uuid the UUID of the risk metrics fact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the risk metrics fact, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get risk metrics fact by UUID", description = "Returns a risk metrics fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved risk metrics fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RiskMetricsFactDTO.class))),
            @ApiResponse(responseCode = "404", description = "Risk metrics fact not found")
    })
    public ResponseEntity<RiskMetricsFactDTO> getRiskMetricsFactByUuid(
            @Parameter(description = "UUID of the risk metrics fact to be obtained", required = true)
            @PathVariable UUID uuid) {
        RiskMetricsFactDTO riskMetricsFact = riskMetricsFactService.findById(uuid);
        return ResponseEntity.ok(riskMetricsFact);
    }

    /**
     * GET /api/risk-metrics/portfolio/{portfolioUuid} : Get risk metrics facts by portfolio UUID.
     *
     * @param portfolioUuid the portfolio UUID of the risk metrics facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of risk metrics facts in body
     */

    /**
     * GET /api/risk-metrics/datetime/{datetimeId} : Get risk metrics facts by datetime ID.
     *
     * @param datetimeId the datetime ID of the risk metrics facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of risk metrics facts in body
     */
    @GetMapping("/datetime/{datetimeId}")
    @Operation(summary = "Get risk metrics facts by datetime ID", description = "Returns risk metrics facts by datetime ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved risk metrics facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RiskMetricsFactDTO.class)))
    })
    public ResponseEntity<List<RiskMetricsFactDTO>> getRiskMetricsFactsByDatetimeId(
            @Parameter(description = "Datetime ID of the risk metrics facts to be obtained", required = true)
            @PathVariable Long datetimeId) {
        List<RiskMetricsFactDTO> riskMetricsFacts = riskMetricsFactService.findByDatetimeId(datetimeId);
        return ResponseEntity.ok(riskMetricsFacts);
    }

    /**
     * POST /api/risk-metrics : Create a new risk metrics fact.
     *
     * @param riskMetricsFactDTO the risk metrics fact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new risk metrics fact
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new risk metrics fact", description = "Creates a new risk metrics fact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created risk metrics fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RiskMetricsFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<RiskMetricsFactDTO> createRiskMetricsFact(
            @Parameter(description = "Risk metrics fact to be created", required = true)
            @Valid @RequestBody RiskMetricsFactDTO riskMetricsFactDTO) {
        RiskMetricsFactDTO createdRiskMetricsFact = riskMetricsFactService.create(riskMetricsFactDTO);
        return new ResponseEntity<>(createdRiskMetricsFact, HttpStatus.CREATED);
    }

    /**
     * PUT /api/risk-metrics/{uuid} : Update an existing risk metrics fact.
     *
     * @param uuid the UUID of the risk metrics fact to update
     * @param riskMetricsFactDTO the risk metrics fact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated risk metrics fact,
     * or with status 404 (Not Found) if the risk metrics fact is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing risk metrics fact", description = "Updates an existing risk metrics fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated risk metrics fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RiskMetricsFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Risk metrics fact not found")
    })
    public ResponseEntity<RiskMetricsFactDTO> updateRiskMetricsFact(
            @Parameter(description = "UUID of the risk metrics fact to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated risk metrics fact information", required = true)
            @Valid @RequestBody RiskMetricsFactDTO riskMetricsFactDTO) {
        RiskMetricsFactDTO updatedRiskMetricsFact = riskMetricsFactService.update(uuid, riskMetricsFactDTO);
        return ResponseEntity.ok(updatedRiskMetricsFact);
    }

    /**
     * DELETE /api/risk-metrics/{uuid} : Delete a risk metrics fact.
     *
     * @param uuid the UUID of the risk metrics fact to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a risk metrics fact", description = "Deletes a risk metrics fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted risk metrics fact"),
            @ApiResponse(responseCode = "404", description = "Risk metrics fact not found")
    })
    public ResponseEntity<Void> deleteRiskMetricsFact(
            @Parameter(description = "UUID of the risk metrics fact to be deleted", required = true)
            @PathVariable UUID uuid) {
        riskMetricsFactService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
