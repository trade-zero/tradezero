package com.trading.api.service;

import com.trading.api.dto.AgentDimDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.AgentDim;
import com.trading.api.repository.AgentDimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for AgentDim entity operations.
 */
@Service
@RequiredArgsConstructor
public class AgentDimService {

    private final AgentDimRepository agentDimRepository;

    /**
     * Get all agents.
     *
     * @return list of all agents
     */
    public List<AgentDimDTO> findAll() {
        return agentDimRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get agent by UUID.
     *
     * @param uuid the agent UUID
     * @return the agent
     * @throws ResourceNotFoundException if agent not found
     */
    public AgentDimDTO findById(UUID uuid) {
        return agentDimRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found with id: " + uuid));
    }

    /**
     * Get agent by name.
     *
     * @param name the agent name
     * @return the agent
     * @throws ResourceNotFoundException if agent not found
     */
    public AgentDimDTO findByName(String name) {
        return agentDimRepository.findByName(name)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found with name: " + name));
    }

    /**
     * Create a new agent.
     *
     * @param agentDimDTO the agent to create
     * @return the created agent
     */
    @Transactional
    public AgentDimDTO create(AgentDimDTO agentDimDTO) {
        AgentDim agentDim = convertToEntity(agentDimDTO);
        agentDim.setAgentDimUuid(null); // Ensure UUID is generated
        AgentDim savedAgentDim = agentDimRepository.save(agentDim);
        return convertToDTO(savedAgentDim);
    }

    /**
     * Update an existing agent.
     *
     * @param uuid the agent UUID
     * @param agentDimDTO the agent data to update
     * @return the updated agent
     * @throws ResourceNotFoundException if agent not found
     */
    @Transactional
    public AgentDimDTO update(UUID uuid, AgentDimDTO agentDimDTO) {
        return agentDimRepository.findById(uuid)
                .map(existingAgent -> {
                    existingAgent.setName(agentDimDTO.getName());
                    return convertToDTO(agentDimRepository.save(existingAgent));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found with id: " + uuid));
    }

    /**
     * Delete an agent by UUID.
     *
     * @param uuid the agent UUID
     * @throws ResourceNotFoundException if agent not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!agentDimRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Agent not found with id: " + uuid);
        }
        agentDimRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param agentDim the entity
     * @return the DTO
     */
    private AgentDimDTO convertToDTO(AgentDim agentDim) {
        AgentDimDTO dto = new AgentDimDTO();
        dto.setAgentDimUuid(agentDim.getAgentDimUuid());
        dto.setName(agentDim.getName());
        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param agentDimDTO the DTO
     * @return the entity
     */
    private AgentDim convertToEntity(AgentDimDTO agentDimDTO) {
        AgentDim entity = new AgentDim();
        entity.setAgentDimUuid(agentDimDTO.getAgentDimUuid());
        entity.setName(agentDimDTO.getName());
        return entity;
    }
}
