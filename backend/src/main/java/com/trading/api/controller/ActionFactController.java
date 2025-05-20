package com.trading.api.controller;

import com.trading.api.dto.ActionFactDTO;
import com.trading.api.service.ActionFactService;
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
 * REST controller for managing ActionFact entities.
 */
@RestController
@RequestMapping("/api/action-facts")
@RequiredArgsConstructor
@Tag(name = "Action Facts", description = "Action facts management APIs")
public class ActionFactController {

    private final ActionFactService actionFactService;

    /**
     * GET /api/action-facts : Get all action facts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of action facts in body
     */
    @GetMapping
    @Operation(summary = "Get all action facts", description = "Returns all action facts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved action facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActionFactDTO.class)))
    })
    public ResponseEntity<List<ActionFactDTO>> getAllActionFacts() {
        List<ActionFactDTO> actionFacts = actionFactService.findAll();
        return ResponseEntity.ok(actionFacts);
    }

    /**
     * GET /api/action-facts/{uuid} : Get action fact by UUID.
     *
     * @param uuid the UUID of the action fact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the action fact, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get action fact by UUID", description = "Returns an action fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved action fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActionFactDTO.class))),
            @ApiResponse(responseCode = "404", description = "Action fact not found")
    })
    public ResponseEntity<ActionFactDTO> getActionFactByUuid(
            @Parameter(description = "UUID of the action fact to be obtained", required = true)
            @PathVariable UUID uuid) {
        ActionFactDTO actionFact = actionFactService.findById(uuid);
        return ResponseEntity.ok(actionFact);
    }

    /**
     * GET /api/action-facts/dimension/{actionDimUuid} : Get action facts by action dimension UUID.
     *
     * @param actionDimUuid the action dimension UUID of the action facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of action facts in body
     */
    @GetMapping("/dimension/{actionDimUuid}")
    @Operation(summary = "Get action facts by action dimension UUID", description = "Returns action facts by action dimension UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved action facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActionFactDTO.class)))
    })
    public ResponseEntity<List<ActionFactDTO>> getActionFactsByActionDimUuid(
            @Parameter(description = "Action dimension UUID of the action facts to be obtained", required = true)
            @PathVariable UUID actionDimUuid) {
        List<ActionFactDTO> actionFacts = actionFactService.findByActionDimUuid(actionDimUuid);
        return ResponseEntity.ok(actionFacts);
    }



    /**
     * GET /api/action-facts/datetime/{datetimeId} : Get action facts by datetime ID.
     *
     * @param datetimeId the datetime ID of the action facts to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of action facts in body
     */
    @GetMapping("/datetime/{datetimeId}")
    @Operation(summary = "Get action facts by datetime ID", description = "Returns action facts by datetime ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved action facts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActionFactDTO.class)))
    })
    public ResponseEntity<List<ActionFactDTO>> getActionFactsByDatetimeId(
            @Parameter(description = "Datetime ID of the action facts to be obtained", required = true)
            @PathVariable Long datetimeId) {
        List<ActionFactDTO> actionFacts = actionFactService.findByDatetimeId(datetimeId);
        return ResponseEntity.ok(actionFacts);
    }

    /**
     * POST /api/action-facts : Create a new action fact.
     *
     * @param actionFactDTO the action fact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new action fact
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new action fact", description = "Creates a new action fact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created action fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActionFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ActionFactDTO> createActionFact(
            @Parameter(description = "Action fact to be created", required = true)
            @Valid @RequestBody ActionFactDTO actionFactDTO) {
        ActionFactDTO createdActionFact = actionFactService.create(actionFactDTO);
        return new ResponseEntity<>(createdActionFact, HttpStatus.CREATED);
    }

    /**
     * PUT /api/action-facts/{uuid} : Update an existing action fact.
     *
     * @param uuid the UUID of the action fact to update
     * @param actionFactDTO the action fact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated action fact,
     * or with status 404 (Not Found) if the action fact is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing action fact", description = "Updates an existing action fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated action fact",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActionFactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Action fact not found")
    })
    public ResponseEntity<ActionFactDTO> updateActionFact(
            @Parameter(description = "UUID of the action fact to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated action fact information", required = true)
            @Valid @RequestBody ActionFactDTO actionFactDTO) {
        ActionFactDTO updatedActionFact = actionFactService.update(uuid, actionFactDTO);
        return ResponseEntity.ok(updatedActionFact);
    }

    /**
     * DELETE /api/action-facts/{uuid} : Delete an action fact.
     *
     * @param uuid the UUID of the action fact to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an action fact", description = "Deletes an action fact by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted action fact"),
            @ApiResponse(responseCode = "404", description = "Action fact not found")
    })
    public ResponseEntity<Void> deleteActionFact(
            @Parameter(description = "UUID of the action fact to be deleted", required = true)
            @PathVariable UUID uuid) {
        actionFactService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
