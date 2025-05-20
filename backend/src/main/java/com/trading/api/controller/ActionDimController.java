package com.trading.api.controller;

import com.trading.api.dto.ActionDimDTO;
import com.trading.api.model.enums.TradeActionType;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import com.trading.api.service.ActionDimService;
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
 * REST controller for managing ActionDim entities.
 */
@RestController
@RequestMapping("/api/actions")
@RequiredArgsConstructor
@Tag(name = "Action", description = "Action management APIs")
public class ActionDimController {

    private final ActionDimService actionDimService;

    /**
     * GET /api/actions : Get all actions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of actions in body
     */
    @GetMapping
    @Operation(summary = "Get all actions", description = "Returns all actions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved actions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActionDimDTO.class)))
    })
    public ResponseEntity<List<ActionDimDTO>> getAllActions() {
        List<ActionDimDTO> actions = actionDimService.findAll();
        return ResponseEntity.ok(actions);
    }

    /**
     * GET /api/actions/{uuid} : Get action by UUID.
     *
     * @param uuid the UUID of the action to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the action, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get action by UUID", description = "Returns an action by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved action",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActionDimDTO.class))),
            @ApiResponse(responseCode = "404", description = "Action not found")
    })
    public ResponseEntity<ActionDimDTO> getActionByUuid(
            @Parameter(description = "UUID of the action to be obtained", required = true)
            @PathVariable UUID uuid) {
        ActionDimDTO action = actionDimService.findById(uuid);
        return ResponseEntity.ok(action);
    }

    /**
     * GET /api/actions/asset-type/{assetType} : Get actions by asset type.
     *
     * @param assetType the asset type of the actions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actions
     */
    @GetMapping("/asset-type/{assetType}")
    @Operation(summary = "Get actions by asset type", description = "Returns actions by asset type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved actions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActionDimDTO.class)))
    })
    public ResponseEntity<List<ActionDimDTO>> getActionsByAssetType(
            @Parameter(description = "Asset type of the actions to be obtained", required = true)
            @PathVariable TradeAssetType assetType) {
        List<ActionDimDTO> actions = actionDimService.findByAssetType(assetType);
        return ResponseEntity.ok(actions);
    }

    /**
     * GET /api/actions/direction-type/{directionType} : Get actions by direction type.
     *
     * @param directionType the direction type of the actions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actions
     */
    @GetMapping("/direction-type/{directionType}")
    @Operation(summary = "Get actions by direction type", description = "Returns actions by direction type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved actions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActionDimDTO.class)))
    })
    public ResponseEntity<List<ActionDimDTO>> getActionsByDirectionType(
            @Parameter(description = "Direction type of the actions to be obtained", required = true)
            @PathVariable TradeDirectionType directionType) {
        List<ActionDimDTO> actions = actionDimService.findByDirectionType(directionType);
        return ResponseEntity.ok(actions);
    }

    /**
     * GET /api/actions/action-type/{actionType} : Get actions by action type.
     *
     * @param actionType the action type of the actions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actions
     */
    @GetMapping("/action-type/{actionType}")
    @Operation(summary = "Get actions by action type", description = "Returns actions by action type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved actions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActionDimDTO.class)))
    })
    public ResponseEntity<List<ActionDimDTO>> getActionsByActionType(
            @Parameter(description = "Action type of the actions to be obtained", required = true)
            @PathVariable TradeActionType actionType) {
        List<ActionDimDTO> actions = actionDimService.findByActionType(actionType);
        return ResponseEntity.ok(actions);
    }

    /**
     * POST /api/actions : Create a new action.
     *
     * @param actionDimDTO the action to create
     * @return the ResponseEntity with status 201 (Created) and with body the new action
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new action", description = "Creates a new action")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created action",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActionDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ActionDimDTO> createAction(
            @Parameter(description = "Action to be created", required = true)
            @Valid @RequestBody ActionDimDTO actionDimDTO) {
        ActionDimDTO createdAction = actionDimService.create(actionDimDTO);
        return new ResponseEntity<>(createdAction, HttpStatus.CREATED);
    }

    /**
     * PUT /api/actions/{uuid} : Update an existing action.
     *
     * @param uuid the UUID of the action to update
     * @param actionDimDTO the action to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated action,
     * or with status 404 (Not Found) if the action is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing action", description = "Updates an existing action by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated action",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActionDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Action not found")
    })
    public ResponseEntity<ActionDimDTO> updateAction(
            @Parameter(description = "UUID of the action to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated action information", required = true)
            @Valid @RequestBody ActionDimDTO actionDimDTO) {
        ActionDimDTO updatedAction = actionDimService.update(uuid, actionDimDTO);
        return ResponseEntity.ok(updatedAction);
    }

    /**
     * DELETE /api/actions/{uuid} : Delete an action.
     *
     * @param uuid the UUID of the action to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an action", description = "Deletes an action by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted action"),
            @ApiResponse(responseCode = "404", description = "Action not found")
    })
    public ResponseEntity<Void> deleteAction(
            @Parameter(description = "UUID of the action to be deleted", required = true)
            @PathVariable UUID uuid) {
        actionDimService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
