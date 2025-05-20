package com.trading.api.controller;

import com.trading.api.dto.DataFeedFactDTO;
import com.trading.api.service.DataFeedFactService;
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
 * REST controller for managing DataFeedFact entities.
 */
@RestController
@RequestMapping("/api/datafeeds")
@RequiredArgsConstructor
@Tag(name = "Data Feed", description = "Data feed management APIs")
public class DataFeedFactController {

    private final DataFeedFactService dataFeedFactService;

    /**
     * GET /api/datafeeds : Get all data feeds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of data feeds in body
     */
    @GetMapping
    @Operation(summary = "Get all data feeds", description = "Returns all data feeds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved data feeds",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataFeedFactDTO.class)))
    })
    public ResponseEntity<List<DataFeedFactDTO>> getAllDataFeeds() {
        List<DataFeedFactDTO> dataFeeds = dataFeedFactService.findAll();
        return ResponseEntity.ok(dataFeeds);
    }

    /**
     * GET /api/datafeeds/{uuid} : Get data feed by UUID.
     *
     * @param uuid the UUID of the data feed to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the data feed, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get data feed by UUID", description = "Returns a data feed by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved data feed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataFeedFactDTO.class))),
            @ApiResponse(responseCode = "404", description = "Data feed not found")
    })
    public ResponseEntity<DataFeedFactDTO> getDataFeedByUuid(
            @Parameter(description = "UUID of the data feed to be obtained", required = true)
            @PathVariable UUID uuid) {
        DataFeedFactDTO dataFeed = dataFeedFactService.findById(uuid);
        return ResponseEntity.ok(dataFeed);
    }

    /**
     * GET /api/datafeeds/name/{name} : Get data feed by name.
     *
     * @param name the name of the data feed to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the data feed, or with status 404 (Not Found)
     */
    @GetMapping("/name/{name}")
    @Operation(summary = "Get data feed by name", description = "Returns a data feed by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved data feed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataFeedFactDTO.class))),
            @ApiResponse(responseCode = "404", description = "Data feed not found")
    })
    public ResponseEntity<DataFeedFactDTO> getDataFeedByName(
            @Parameter(description = "Name of the data feed to be obtained", required = true)
            @PathVariable String name) {
        DataFeedFactDTO dataFeed = dataFeedFactService.findByName(name);
        return ResponseEntity.ok(dataFeed);
    }

    /**
     * POST /api/datafeeds : Create a new data feed.
     *
     * @param dataFeedFactDTO the data feed to create
     * @return the ResponseEntity with status 201 (Created) and with body the new data feed
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new data feed", description = "Creates a new data feed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created data feed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataFeedFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<DataFeedFactDTO> createDataFeed(
            @Parameter(description = "Data feed to be created", required = true)
            @Valid @RequestBody DataFeedFactDTO dataFeedFactDTO) {
        DataFeedFactDTO createdDataFeed = dataFeedFactService.create(dataFeedFactDTO);
        return new ResponseEntity<>(createdDataFeed, HttpStatus.CREATED);
    }

    /**
     * PUT /api/datafeeds/{uuid} : Update an existing data feed.
     *
     * @param uuid the UUID of the data feed to update
     * @param dataFeedFactDTO the data feed to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated data feed,
     * or with status 404 (Not Found) if the data feed is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing data feed", description = "Updates an existing data feed by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated data feed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataFeedFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Data feed not found")
    })
    public ResponseEntity<DataFeedFactDTO> updateDataFeed(
            @Parameter(description = "UUID of the data feed to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated data feed information", required = true)
            @Valid @RequestBody DataFeedFactDTO dataFeedFactDTO) {
        DataFeedFactDTO updatedDataFeed = dataFeedFactService.update(uuid, dataFeedFactDTO);
        return ResponseEntity.ok(updatedDataFeed);
    }

    /**
     * DELETE /api/datafeeds/{uuid} : Delete a data feed.
     *
     * @param uuid the UUID of the data feed to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a data feed", description = "Deletes a data feed by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted data feed"),
            @ApiResponse(responseCode = "404", description = "Data feed not found")
    })
    public ResponseEntity<Void> deleteDataFeed(
            @Parameter(description = "UUID of the data feed to be deleted", required = true)
            @PathVariable UUID uuid) {
        dataFeedFactService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
