package com.trading.api.controller;

import com.trading.api.dto.DateTimeDimDTO;
import com.trading.api.service.DateTimeDimService;
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

/**
 * REST controller for managing DateTimeDim entities.
 */
@RestController
@RequestMapping("/api/datetimes")
@RequiredArgsConstructor
@Tag(name = "DateTime", description = "DateTime management APIs")
public class DateTimeDimController {

    private final DateTimeDimService dateTimeDimService;

    /**
     * GET /api/datetimes : Get all datetime records.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of datetime records in body
     */
    @GetMapping
    @Operation(summary = "Get all datetime records", description = "Returns all datetime records")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved datetime records",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DateTimeDimDTO.class)))
    })
    public ResponseEntity<List<DateTimeDimDTO>> getAllDateTimes() {
        List<DateTimeDimDTO> dateTimes = dateTimeDimService.findAll();
        return ResponseEntity.ok(dateTimes);
    }

    /**
     * GET /api/datetimes/{id} : Get datetime by ID.
     *
     * @param id the ID of the datetime to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the datetime, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get datetime by ID", description = "Returns a datetime by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved datetime",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DateTimeDimDTO.class))),
            @ApiResponse(responseCode = "404", description = "Datetime not found")
    })
    public ResponseEntity<DateTimeDimDTO> getDateTimeById(
            @Parameter(description = "ID of the datetime to be obtained", required = true)
            @PathVariable Long id) {
        DateTimeDimDTO dateTime = dateTimeDimService.findById(id);
        return ResponseEntity.ok(dateTime);
    }

    /**
     * POST /api/datetimes : Create a new datetime.
     *
     * @param dateTimeDimDTO the datetime to create
     * @return the ResponseEntity with status 201 (Created) and with body the new datetime
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new datetime", description = "Creates a new datetime")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created datetime",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DateTimeDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<DateTimeDimDTO> createDateTime(
            @Parameter(description = "Datetime to be created", required = true)
            @Valid @RequestBody DateTimeDimDTO dateTimeDimDTO) {
        DateTimeDimDTO createdDateTime = dateTimeDimService.create(dateTimeDimDTO);
        return new ResponseEntity<>(createdDateTime, HttpStatus.CREATED);
    }

    /**
     * PUT /api/datetimes/{id} : Update an existing datetime.
     *
     * @param id the ID of the datetime to update
     * @param dateTimeDimDTO the datetime to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated datetime,
     * or with status 404 (Not Found) if the datetime is not found
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing datetime", description = "Updates an existing datetime by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated datetime",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DateTimeDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Datetime not found")
    })
    public ResponseEntity<DateTimeDimDTO> updateDateTime(
            @Parameter(description = "ID of the datetime to be updated", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated datetime information", required = true)
            @Valid @RequestBody DateTimeDimDTO dateTimeDimDTO) {
        DateTimeDimDTO updatedDateTime = dateTimeDimService.update(id, dateTimeDimDTO);
        return ResponseEntity.ok(updatedDateTime);
    }

    /**
     * DELETE /api/datetimes/{id} : Delete a datetime.
     *
     * @param id the ID of the datetime to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a datetime", description = "Deletes a datetime by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted datetime"),
            @ApiResponse(responseCode = "404", description = "Datetime not found")
    })
    public ResponseEntity<Void> deleteDateTime(
            @Parameter(description = "ID of the datetime to be deleted", required = true)
            @PathVariable Long id) {
        dateTimeDimService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
