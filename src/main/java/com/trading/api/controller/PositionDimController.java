package com.trading.api.controller;

import com.trading.api.dto.PositionDimDTO;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import com.trading.api.service.PositionDimService;
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
 * REST controller for managing PositionDim entities.
 */
@RestController
@RequestMapping("/api/position-dimensions")
@RequiredArgsConstructor
@Tag(name = "Position Dimension", description = "Position dimension management APIs")
public class PositionDimController {

    private final PositionDimService positionDimService;

    /**
     * GET /api/position-dimensions : Get all position dimensions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of position dimensions in body
     */
    @GetMapping
    @Operation(summary = "Get all position dimensions", description = "Returns all position dimensions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved position dimensions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PositionDimDTO.class)))
    })
    public ResponseEntity<List<PositionDimDTO>> getAllPositionDimensions() {
        List<PositionDimDTO> positionDimensions = positionDimService.findAll();
        return ResponseEntity.ok(positionDimensions);
    }

    /**
     * GET /api/position-dimensions/{uuid} : Get position dimension by UUID.
     *
     * @param uuid the UUID of the position dimension to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the position dimension, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get position dimension by UUID", description = "Returns a position dimension by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved position dimension",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PositionDimDTO.class))),
            @ApiResponse(responseCode = "404", description = "Position dimension not found")
    })
    public ResponseEntity<PositionDimDTO> getPositionDimensionByUuid(
            @Parameter(description = "UUID of the position dimension to be obtained", required = true)
            @PathVariable UUID uuid) {
        PositionDimDTO positionDimension = positionDimService.findById(uuid);
        return ResponseEntity.ok(positionDimension);
    }

    /**
     * GET /api/position-dimensions/direction/{directionType} : Get position dimensions by direction type.
     *
     * @param directionType the direction type of the position dimensions to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of position dimensions in body
     */
    @GetMapping("/direction/{directionType}")
    @Operation(summary = "Get position dimensions by direction type", description = "Returns position dimensions by direction type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved position dimensions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PositionDimDTO.class)))
    })
    public ResponseEntity<List<PositionDimDTO>> getPositionDimensionsByDirectionType(
            @Parameter(description = "Direction type of the position dimensions to be obtained", required = true)
            @PathVariable TradeDirectionType directionType) {
        List<PositionDimDTO> positionDimensions = positionDimService.findByDirectionType(directionType);
        return ResponseEntity.ok(positionDimensions);
    }

    /**
     * GET /api/position-dimensions/asset/{assetType} : Get position dimensions by asset type.
     *
     * @param assetType the asset type of the position dimensions to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of position dimensions in body
     */
    @GetMapping("/asset/{assetType}")
    @Operation(summary = "Get position dimensions by asset type", description = "Returns position dimensions by asset type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved position dimensions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PositionDimDTO.class)))
    })
    public ResponseEntity<List<PositionDimDTO>> getPositionDimensionsByAssetType(
            @Parameter(description = "Asset type of the position dimensions to be obtained", required = true)
            @PathVariable TradeAssetType assetType) {
        List<PositionDimDTO> positionDimensions = positionDimService.findByAssetType(assetType);
        return ResponseEntity.ok(positionDimensions);
    }

    /**
     * POST /api/position-dimensions : Create a new position dimension.
     *
     * @param positionDimDTO the position dimension to create
     * @return the ResponseEntity with status 201 (Created) and with body the new position dimension
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new position dimension", description = "Creates a new position dimension")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created position dimension",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PositionDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<PositionDimDTO> createPositionDimension(
            @Parameter(description = "Position dimension to be created", required = true)
            @Valid @RequestBody PositionDimDTO positionDimDTO) {
        PositionDimDTO createdPositionDimension = positionDimService.create(positionDimDTO);
        return new ResponseEntity<>(createdPositionDimension, HttpStatus.CREATED);
    }

    /**
     * PUT /api/position-dimensions/{uuid} : Update an existing position dimension.
     *
     * @param uuid the UUID of the position dimension to update
     * @param positionDimDTO the position dimension to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated position dimension,
     * or with status 404 (Not Found) if the position dimension is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing position dimension", description = "Updates an existing position dimension by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated position dimension",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PositionDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Position dimension not found")
    })
    public ResponseEntity<PositionDimDTO> updatePositionDimension(
            @Parameter(description = "UUID of the position dimension to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated position dimension information", required = true)
            @Valid @RequestBody PositionDimDTO positionDimDTO) {
        PositionDimDTO updatedPositionDimension = positionDimService.update(uuid, positionDimDTO);
        return ResponseEntity.ok(updatedPositionDimension);
    }

    /**
     * DELETE /api/position-dimensions/{uuid} : Delete a position dimension.
     *
     * @param uuid the UUID of the position dimension to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a position dimension", description = "Deletes a position dimension by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted position dimension"),
            @ApiResponse(responseCode = "404", description = "Position dimension not found")
    })
    public ResponseEntity<Void> deletePositionDimension(
            @Parameter(description = "UUID of the position dimension to be deleted", required = true)
            @PathVariable UUID uuid) {
        positionDimService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
