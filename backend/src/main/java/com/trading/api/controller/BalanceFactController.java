package com.trading.api.controller;

import com.trading.api.dto.BalanceFactDTO;
import com.trading.api.service.BalanceFactService;
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
 * REST controller for managing BalanceFact entities.
 */
@RestController
@RequestMapping("/api/balances")
@RequiredArgsConstructor
@Tag(name = "Balance", description = "Balance management APIs")
public class BalanceFactController {

    private final BalanceFactService balanceFactService;

    /**
     * GET /api/balances : Get all balance facts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of balance facts in body
     */
    @GetMapping
    @Operation(summary = "Get all balance facts", description = "Returns all balance facts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved balance facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceFactDTO.class)))
    })
    public ResponseEntity<List<BalanceFactDTO>> getAllBalanceFacts() {
        List<BalanceFactDTO> balanceFacts = balanceFactService.findAll();
        return ResponseEntity.ok(balanceFacts);
    }

    /**
     * GET /api/balances/{uuid} : Get balance fact by UUID.
     *
     * @param uuid the UUID of the balance fact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the balance fact, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get balance fact by UUID", description = "Returns a balance fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved balance fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceFactDTO.class))),
            @ApiResponse(responseCode = "404", description = "Balance fact not found")
    })
    public ResponseEntity<BalanceFactDTO> getBalanceFactByUuid(
            @Parameter(description = "UUID of the balance fact to be obtained", required = true)
            @PathVariable UUID uuid) {
        BalanceFactDTO balanceFact = balanceFactService.findById(uuid);
        return ResponseEntity.ok(balanceFact);
    }

    /**
     * GET /api/balances/portfolio/{portfolioUuid} : Get balance facts by portfolio UUID.
     *
     * @param portfolioUuid the portfolio UUID of the balance facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of balance facts in body
     */
    @GetMapping("/portfolio/{portfolioUuid}")
    @Operation(summary = "Get balance facts by portfolio UUID", description = "Returns balance facts by portfolio UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved balance facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceFactDTO.class)))
    })
    public ResponseEntity<List<BalanceFactDTO>> getBalanceFactsByPortfolioUuid(
            @Parameter(description = "Portfolio UUID of the balance facts to be obtained", required = true)
            @PathVariable UUID portfolioUuid) {
        List<BalanceFactDTO> balanceFacts = balanceFactService.findByPortfolioUuid(portfolioUuid);
        return ResponseEntity.ok(balanceFacts);
    }

    /**
     * GET /api/balances/datetime/{datetimeId} : Get balance facts by datetime ID.
     *
     * @param datetimeId the datetime ID of the balance facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of balance facts in body
     */
    @GetMapping("/datetime/{datetimeId}")
    @Operation(summary = "Get balance facts by datetime ID", description = "Returns balance facts by datetime ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved balance facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceFactDTO.class)))
    })
    public ResponseEntity<List<BalanceFactDTO>> getBalanceFactsByDatetimeId(
            @Parameter(description = "Datetime ID of the balance facts to be obtained", required = true)
            @PathVariable Long datetimeId) {
        List<BalanceFactDTO> balanceFacts = balanceFactService.findByDatetimeId(datetimeId);
        return ResponseEntity.ok(balanceFacts);
    }

    /**
     * POST /api/balances : Create a new balance fact.
     *
     * @param balanceFactDTO the balance fact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new balance fact
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new balance fact", description = "Creates a new balance fact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created balance fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<BalanceFactDTO> createBalanceFact(
            @Parameter(description = "Balance fact to be created", required = true)
            @Valid @RequestBody BalanceFactDTO balanceFactDTO) {
        BalanceFactDTO createdBalanceFact = balanceFactService.create(balanceFactDTO);
        return new ResponseEntity<>(createdBalanceFact, HttpStatus.CREATED);
    }

    /**
     * PUT /api/balances/{uuid} : Update an existing balance fact.
     *
     * @param uuid the UUID of the balance fact to update
     * @param balanceFactDTO the balance fact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated balance fact,
     * or with status 404 (Not Found) if the balance fact is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing balance fact", description = "Updates an existing balance fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated balance fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Balance fact not found")
    })
    public ResponseEntity<BalanceFactDTO> updateBalanceFact(
            @Parameter(description = "UUID of the balance fact to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated balance fact information", required = true)
            @Valid @RequestBody BalanceFactDTO balanceFactDTO) {
        BalanceFactDTO updatedBalanceFact = balanceFactService.update(uuid, balanceFactDTO);
        return ResponseEntity.ok(updatedBalanceFact);
    }

    /**
     * DELETE /api/balances/{uuid} : Delete a balance fact.
     *
     * @param uuid the UUID of the balance fact to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a balance fact", description = "Deletes a balance fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted balance fact"),
            @ApiResponse(responseCode = "404", description = "Balance fact not found")
    })
    public ResponseEntity<Void> deleteBalanceFact(
            @Parameter(description = "UUID of the balance fact to be deleted", required = true)
            @PathVariable UUID uuid) {
        balanceFactService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
