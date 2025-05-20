package com.trading.api.controller;

import com.trading.api.dto.PortfolioFactDTO;
import com.trading.api.service.PortfolioFactService;
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
 * REST controller for managing PortfolioFact entities.
 */
@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
@Tag(name = "Portfolio", description = "Portfolio management APIs")
public class PortfolioFactController {

    private final PortfolioFactService portfolioFactService;

    /**
     * GET /api/portfolios : Get all portfolios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of portfolios in body
     */
    @GetMapping
    @Operation(summary = "Get all portfolios", description = "Returns all portfolios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved portfolios",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PortfolioFactDTO.class)))
    })
    public ResponseEntity<List<PortfolioFactDTO>> getAllPortfolios() {
        List<PortfolioFactDTO> portfolios = portfolioFactService.findAll();
        return ResponseEntity.ok(portfolios);
    }

    /**
     * GET /api/portfolios/{uuid} : Get portfolio by UUID.
     *
     * @param uuid the UUID of the portfolio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the portfolio, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get portfolio by UUID", description = "Returns a portfolio by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved portfolio",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PortfolioFactDTO.class))),
            @ApiResponse(responseCode = "404", description = "Portfolio not found")
    })
    public ResponseEntity<PortfolioFactDTO> getPortfolioByUuid(
            @Parameter(description = "UUID of the portfolio to be obtained", required = true)
            @PathVariable UUID uuid) {
        PortfolioFactDTO portfolio = portfolioFactService.findById(uuid);
        return ResponseEntity.ok(portfolio);
    }

    /**
     * GET /api/portfolios/tradezero/{tradeZeroFactUuid} : Get portfolios by trade zero fact UUID.
     *
     * @param tradeZeroFactUuid the trade zero fact UUID of the portfolios to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of portfolios in body
     */
    @GetMapping("/tradezero/{tradeZeroFactUuid}")
    @Operation(summary = "Get portfolios by trade zero fact UUID", description = "Returns portfolios by trade zero fact UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved portfolios",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PortfolioFactDTO.class)))
    })
    public ResponseEntity<List<PortfolioFactDTO>> getPortfoliosByTradeZeroFactUuid(
            @Parameter(description = "Trade zero fact UUID of the portfolios to be obtained", required = true)
            @PathVariable UUID tradeZeroFactUuid) {
        List<PortfolioFactDTO> portfolios = portfolioFactService.findByTradeZeroFactUuid(tradeZeroFactUuid);
        return ResponseEntity.ok(portfolios);
    }

    /**
     * GET /api/portfolios/name/{name} : Get portfolio by name.
     *
     * @param name the name of the portfolio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the portfolio, or with status 404 (Not Found)
     */
    @GetMapping("/name/{name}")
    @Operation(summary = "Get portfolio by name", description = "Returns a portfolio by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved portfolio",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PortfolioFactDTO.class))),
            @ApiResponse(responseCode = "404", description = "Portfolio not found")
    })
    public ResponseEntity<PortfolioFactDTO> getPortfolioByName(
            @Parameter(description = "Name of the portfolio to be obtained", required = true)
            @PathVariable String name) {
        PortfolioFactDTO portfolio = portfolioFactService.findByName(name);
        return ResponseEntity.ok(portfolio);
    }

    /**
     * POST /api/portfolios : Create a new portfolio.
     *
     * @param portfolioFactDTO the portfolio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new portfolio
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new portfolio", description = "Creates a new portfolio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created portfolio",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PortfolioFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<PortfolioFactDTO> createPortfolio(
            @Parameter(description = "Portfolio to be created", required = true)
            @Valid @RequestBody PortfolioFactDTO portfolioFactDTO) {
        PortfolioFactDTO createdPortfolio = portfolioFactService.create(portfolioFactDTO);
        return new ResponseEntity<>(createdPortfolio, HttpStatus.CREATED);
    }

    /**
     * PUT /api/portfolios/{uuid} : Update an existing portfolio.
     *
     * @param uuid the UUID of the portfolio to update
     * @param portfolioFactDTO the portfolio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated portfolio,
     * or with status 404 (Not Found) if the portfolio is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing portfolio", description = "Updates an existing portfolio by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated portfolio",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PortfolioFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Portfolio not found")
    })
    public ResponseEntity<PortfolioFactDTO> updatePortfolio(
            @Parameter(description = "UUID of the portfolio to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated portfolio information", required = true)
            @Valid @RequestBody PortfolioFactDTO portfolioFactDTO) {
        PortfolioFactDTO updatedPortfolio = portfolioFactService.update(uuid, portfolioFactDTO);
        return ResponseEntity.ok(updatedPortfolio);
    }

    /**
     * DELETE /api/portfolios/{uuid} : Delete a portfolio.
     *
     * @param uuid the UUID of the portfolio to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a portfolio", description = "Deletes a portfolio by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted portfolio"),
            @ApiResponse(responseCode = "404", description = "Portfolio not found")
    })
    public ResponseEntity<Void> deletePortfolio(
            @Parameter(description = "UUID of the portfolio to be deleted", required = true)
            @PathVariable UUID uuid) {
        portfolioFactService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
