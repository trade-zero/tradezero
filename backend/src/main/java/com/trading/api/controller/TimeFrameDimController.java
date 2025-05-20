package com.trading.api.controller;

import com.trading.api.dto.TimeFrameDimDTO;
import com.trading.api.model.enums.TradeTimeFrameType;
import com.trading.api.service.TimeFrameDimService;
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
 * REST controller for managing TimeFrameDim entities.
 */
@RestController
@RequestMapping("/api/timeframes")
@RequiredArgsConstructor
@Tag(name = "Time Frame", description = "Time frame management APIs")
public class TimeFrameDimController {

    private final TimeFrameDimService timeFrameDimService;

    /**
     * GET /api/timeframes : Get all time frames.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of time frames in body
     */
    @GetMapping
    @Operation(summary = "Get all time frames", description = "Returns all time frames")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved time frames",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TimeFrameDimDTO.class)))
    })
    public ResponseEntity<List<TimeFrameDimDTO>> getAllTimeFrames() {
        List<TimeFrameDimDTO> timeFrames = timeFrameDimService.findAll();
        return ResponseEntity.ok(timeFrames);
    }

    /**
     * GET /api/timeframes/{timeFrame} : Get time frame by time frame type.
     *
     * @param timeFrame the time frame type of the time frame to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the time frame, or with status 404 (Not Found)
     */
    @GetMapping("/{timeFrame}")
    @Operation(summary = "Get time frame by time frame type", description = "Returns a time frame by time frame type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved time frame",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TimeFrameDimDTO.class))),
            @ApiResponse(responseCode = "404", description = "Time frame not found")
    })
    public ResponseEntity<TimeFrameDimDTO> getTimeFrameByTimeFrameType(
            @Parameter(description = "Time frame type of the time frame to be obtained", required = true)
            @PathVariable TradeTimeFrameType timeFrame) {
        TimeFrameDimDTO timeFrameDTO = timeFrameDimService.findById(timeFrame);
        return ResponseEntity.ok(timeFrameDTO);
    }

    /**
     * POST /api/timeframes : Create a new time frame.
     *
     * @param timeFrameDimDTO the time frame to create
     * @return the ResponseEntity with status 201 (Created) and with body the new time frame
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new time frame", description = "Creates a new time frame")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created time frame",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TimeFrameDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<TimeFrameDimDTO> createTimeFrame(
            @Parameter(description = "Time frame to be created", required = true)
            @Valid @RequestBody TimeFrameDimDTO timeFrameDimDTO) {
        TimeFrameDimDTO createdTimeFrame = timeFrameDimService.create(timeFrameDimDTO);
        return new ResponseEntity<>(createdTimeFrame, HttpStatus.CREATED);
    }

    /**
     * PUT /api/timeframes/{timeFrame} : Update an existing time frame.
     *
     * @param timeFrame the time frame type of the time frame to update
     * @param timeFrameDimDTO the time frame to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated time frame,
     * or with status 404 (Not Found) if the time frame is not found
     */
    @PutMapping("/{timeFrame}")
    @Operation(summary = "Update an existing time frame", description = "Updates an existing time frame by time frame type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated time frame",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TimeFrameDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Time frame not found")
    })
    public ResponseEntity<TimeFrameDimDTO> updateTimeFrame(
            @Parameter(description = "Time frame type of the time frame to be updated", required = true)
            @PathVariable TradeTimeFrameType timeFrame,
            @Parameter(description = "Updated time frame information", required = true)
            @Valid @RequestBody TimeFrameDimDTO timeFrameDimDTO) {
        TimeFrameDimDTO updatedTimeFrame = timeFrameDimService.update(timeFrame, timeFrameDimDTO);
        return ResponseEntity.ok(updatedTimeFrame);
    }

    /**
     * DELETE /api/timeframes/{timeFrame} : Delete a time frame.
     *
     * @param timeFrame the time frame type of the time frame to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{timeFrame}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a time frame", description = "Deletes a time frame by time frame type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted time frame"),
            @ApiResponse(responseCode = "404", description = "Time frame not found")
    })
    public ResponseEntity<Void> deleteTimeFrame(
            @Parameter(description = "Time frame type of the time frame to be deleted", required = true)
            @PathVariable TradeTimeFrameType timeFrame) {
        timeFrameDimService.delete(timeFrame);
        return ResponseEntity.noContent().build();
    }
}
