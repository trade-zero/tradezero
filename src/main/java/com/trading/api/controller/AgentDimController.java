package com.trading.api.controller;

import com.trading.api.dto.AgentDimDTO;
import com.trading.api.service.AgentDimService;
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
 * REST controller for managing AgentDim entities.
 */
@RestController
@RequestMapping("/api/agents")
@RequiredArgsConstructor
@Tag(name = "Agent", description = "Agent management APIs")
public class AgentDimController {

    private final AgentDimService agentDimService;

    /**
     * GET /api/agents : Get all agents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of agents in body
     */
    @GetMapping
    @Operation(summary = "Get all agents", description = "Returns all agents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved agents",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgentDimDTO.class)))
    })
    public ResponseEntity<List<AgentDimDTO>> getAllAgents() {
        List<AgentDimDTO> agents = agentDimService.findAll();
        return ResponseEntity.ok(agents);
    }

    /**
     * GET /api/agents/{uuid} : Get agent by UUID.
     *
     * @param uuid the UUID of the agent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agent, or with status 404 (Not Found)
     */
    @GetMapping("/{uuid}")
    @Operation(summary = "Get agent by UUID", description = "Returns an agent by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved agent",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgentDimDTO.class))),
            @ApiResponse(responseCode = "404", description = "Agent not found")
    })
    public ResponseEntity<AgentDimDTO> getAgentByUuid(
            @Parameter(description = "UUID of the agent to be obtained", required = true)
            @PathVariable UUID uuid) {
        AgentDimDTO agent = agentDimService.findById(uuid);
        return ResponseEntity.ok(agent);
    }

    /**
     * GET /api/agents/name/{name} : Get agent by name.
     *
     * @param name the name of the agent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agent, or with status 404 (Not Found)
     */
    @GetMapping("/name/{name}")
    @Operation(summary = "Get agent by name", description = "Returns an agent by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved agent",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgentDimDTO.class))),
            @ApiResponse(responseCode = "404", description = "Agent not found")
    })
    public ResponseEntity<AgentDimDTO> getAgentByName(
            @Parameter(description = "Name of the agent to be obtained", required = true)
            @PathVariable String name) {
        AgentDimDTO agent = agentDimService.findByName(name);
        return ResponseEntity.ok(agent);
    }

    /**
     * POST /api/agents : Create a new agent.
     *
     * @param agentDimDTO the agent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agent
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new agent", description = "Creates a new agent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created agent",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgentDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<AgentDimDTO> createAgent(
            @Parameter(description = "Agent to be created", required = true)
            @Valid @RequestBody AgentDimDTO agentDimDTO) {
        AgentDimDTO createdAgent = agentDimService.create(agentDimDTO);
        return new ResponseEntity<>(createdAgent, HttpStatus.CREATED);
    }

    /**
     * PUT /api/agents/{uuid} : Update an existing agent.
     *
     * @param uuid the UUID of the agent to update
     * @param agentDimDTO the agent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agent,
     * or with status 404 (Not Found) if the agent is not found
     */
    @PutMapping("/{uuid}")
    @Operation(summary = "Update an existing agent", description = "Updates an existing agent by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated agent",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgentDimDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Agent not found")
    })
    public ResponseEntity<AgentDimDTO> updateAgent(
            @Parameter(description = "UUID of the agent to be updated", required = true)
            @PathVariable UUID uuid,
            @Parameter(description = "Updated agent information", required = true)
            @Valid @RequestBody AgentDimDTO agentDimDTO) {
        AgentDimDTO updatedAgent = agentDimService.update(uuid, agentDimDTO);
        return ResponseEntity.ok(updatedAgent);
    }

    /**
     * DELETE /api/agents/{uuid} : Delete an agent.
     *
     * @param uuid the UUID of the agent to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an agent", description = "Deletes an agent by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted agent"),
            @ApiResponse(responseCode = "404", description = "Agent not found")
    })
    public ResponseEntity<Void> deleteAgent(
            @Parameter(description = "UUID of the agent to be deleted", required = true)
            @PathVariable UUID uuid) {
        agentDimService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
