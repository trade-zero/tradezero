package com.trading.api.controller;

import com.trading.api.dto.PositionFactDTO;
import com.trading.api.service.PositionFactService;
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
 * REST controller for managing PositionFact entities.
 */
@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
@Tag(name = "Position", description = "Position management APIs")
public class PositionFactController {

    private final PositionFactService positionFactService;

    /**
     * GET /api/positions : Get all position facts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of position facts in body
     */
    @GetMapping
    @Operation(summary = "Get all position facts", description = "Returns all position facts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved position facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PositionFactDTO.class)))
    })
    public ResponseEntity<List<PositionFactDTO>> getAllPositionFacts() {
        List<PositionFactDTO> positionFacts = positionFactService.findAll();
        return ResponseEntity.ok(positionFacts);
    }

    /**
     * GET /api/positions/{uuid} : Get position fact by UUID.
     *
     * @param uuid the UUID of the position fact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the position fact, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get position fact by UUID", description = "Returns a position fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved position fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PositionFactDTO.class))),
            @ApiResponse(responseCode = "404", description = "Position fact not found")
    })
    public ResponseEntity<PositionFactDTO> getPositionFactByUuid(
            @Parameter(description = "UUID of the position fact to be obtained", required = true)
            @PathVariable UUID uuid) {
        PositionFactDTO positionFact = positionFactService.findById(uuid);
        return ResponseEntity.ok(positionFact);
    }

    /**
     * GET /api/positions/dimension/{positionDimUuid} : Get position facts by position dimension UUID.
     *
     * @param positionDimUuid the position dimension UUID of the position facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of position facts in body
     */
    @GetMapping("/dimension/{positionDimUuid}")
    @Operation(summary = "Get position facts by position dimension UUID", description = "Returns position facts by position dimension UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved position facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PositionFactDTO.class)))
    })
    public ResponseEntity<List<PositionFactDTO>> getPositionFactsByPositionDimUuid(
            @Parameter(description = "Position dimension UUID of the position facts to be obtained", required = true)
            @PathVariable UUID positionDimUuid) {
        List<PositionFactDTO> positionFacts = positionFactService.findByPositionDimUuid(positionDimUuid);
        return ResponseEntity.ok(positionFacts);
    }

    /**
     * GET /api/positions/portfolio/{portfolioUuid} : Get position facts by portfolio UUID.
     *
     * @param portfolioUuid the portfolio UUID of the position facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of position facts in body
     */
    @GetMapping("/portfolio/{portfolioUuid}")
    @Operation(summary = "Get position facts by portfolio UUID", description = "Returns position facts by portfolio UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved position facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PositionFactDTO.class)))
    })
    public ResponseEntity<List<PositionFactDTO>> getPositionFactsByPortfolioUuid(
            @Parameter(description = "Portfolio UUID of the position facts to be obtained", required = true)
            @PathVariable UUID portfolioUuid) {
        List<PositionFactDTO> positionFacts = positionFactService.findByPortfolioUuid(portfolioUuid);
        return ResponseEntity.ok(positionFacts);
    }

    /**
     * GET /api/positions/datetime/{datetimeId} : Get position facts by datetime ID.
     *
     * @param datetimeId the datetime ID of the position facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of position facts in body
     */
    @GetMapping("/datetime/{datetimeId}")
    @Operation(summary = "Get position facts by datetime ID", description = "Returns position facts by datetime ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved position facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PositionFactDTO.class)))
    })
    public ResponseEntity<List<PositionFactDTO>> getPositionFactsByDatetimeId(
            @Parameter(description = "Datetime ID of the position facts to be obtained", required = true)
            @PathVariable Long datetimeId) {
        List<PositionFactDTO> positionFacts = positionFactService.findByDatetimeId(datetimeId);
        return ResponseEntity.ok(positionFacts);
    }

    /**
     * POST /api/positions : Create a new position fact.
     *
     * @param positionFactDTO the position fact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new position fact
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new position fact", description = "Creates a new position fact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created position fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PositionFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<PositionFactDTO> createPositionFact(
            @Parameter(description = "Position fact to be created", required = true)
            @Valid @RequestBody PositionFactDTO positionFactDTO) {
        PositionFactDTO createdPositionFact = positionFactService.create(positionFactDTO);
        return new ResponseEntity<>(createdPositionFact, HttpStatus.CREATED);
    }

    /**
     * PUT /api/positions/{uuid} : Update an existing position fact.
     *
     * @param uuid the UUID of the position fact to update
     * @param positionFactDTO the position fact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated position fact,
     * or with status 404 (Not Found) if the position fact is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing position fact", description = "Updates an existing position fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated position fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PositionFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Position fact not found")
    })
    public ResponseEntity<PositionFactDTO> updatePositionFact(
            @Parameter(description = "UUID of the position fact to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated position fact information", required = true)
            @Valid @RequestBody PositionFactDTO positionFactDTO) {
        PositionFactDTO updatedPositionFact = positionFactService.update(uuid, positionFactDTO);
        return ResponseEntity.ok(updatedPositionFact);
    }

    /**
     * DELETE /api/positions/{uuid} : Delete a position fact.
     *
     * @param uuid the UUID of the position fact to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a position fact", description = "Deletes a position fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted position fact"),
            @ApiResponse(responseCode = "404", description = "Position fact not found")
    })
    public ResponseEntity<Void> deletePositionFact(
            @Parameter(description = "UUID of the position fact to be deleted", required = true)
            @PathVariable UUID uuid) {
        positionFactService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
