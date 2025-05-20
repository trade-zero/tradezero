package com.trading.api.service;

import com.trading.api.dto.ActionFactDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.ActionDim;
import com.trading.api.model.ActionFact;
import com.trading.api.model.DateTimeDim;
import com.trading.api.model.RiskManagementFact;
import com.trading.api.repository.ActionDimRepository;
import com.trading.api.repository.ActionFactRepository;
import com.trading.api.repository.DateTimeDimRepository;
import com.trading.api.repository.RiskManagementFactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for ActionFact entity operations.
 */
@Service
@RequiredArgsConstructor
public class ActionFactService {

    private final ActionFactRepository actionFactRepository;
    private final RiskManagementFactRepository riskManagementFactRepository;
    private final ActionDimRepository actionDimRepository;
    private final DateTimeDimRepository dateTimeDimRepository;

    /**
     * Get all action facts.
     *
     * @return list of all action facts
     */
    public List<ActionFactDTO> findAll() {
        return actionFactRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get action fact by UUID.
     *
     * @param uuid the action fact UUID
     * @return the action fact
     * @throws ResourceNotFoundException if action fact not found
     */
    public ActionFactDTO findById(UUID uuid) {
        return actionFactRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Action fact not found with id: " + uuid));
    }

    /**
     * Get action facts by risk management UUID.
     *
     * @param riskManagementUuid the risk management UUID
     * @return list of action facts
     */
    public List<ActionFactDTO> findByRiskManagementUuid(UUID riskManagementUuid) {
        return actionFactRepository.findByRiskManagement_RiskManagementUuid(riskManagementUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get action facts by action dimension UUID.
     *
     * @param actionDimUuid the action dimension UUID
     * @return list of action facts
     */
    public List<ActionFactDTO> findByActionDimUuid(UUID actionDimUuid) {
        return actionFactRepository.findByActionDim_ActionDimUuid(actionDimUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get action facts by datetime ID.
     *
     * @param datetimeId the datetime ID
     * @return list of action facts
     */
    public List<ActionFactDTO> findByDatetimeId(Long datetimeId) {
        return actionFactRepository.findByDateTime_DatetimeId(datetimeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new action fact.
     *
     * @param actionFactDTO the action fact to create
     * @return the created action fact
     */
    @Transactional
    public ActionFactDTO create(ActionFactDTO actionFactDTO) {
        ActionFact actionFact = convertToEntity(actionFactDTO);
        actionFact.setActionFactUuid(null); // Ensure UUID is generated
        ActionFact savedActionFact = actionFactRepository.save(actionFact);
        return convertToDTO(savedActionFact);
    }

    /**
     * Update an existing action fact.
     *
     * @param uuid the action fact UUID
     * @param actionFactDTO the action fact data to update
     * @return the updated action fact
     * @throws ResourceNotFoundException if action fact not found
     */
    @Transactional
    public ActionFactDTO update(UUID uuid, ActionFactDTO actionFactDTO) {
        return actionFactRepository.findById(uuid)
                .map(existingActionFact -> {
                    // Find related entities
                    RiskManagementFact riskManagement = riskManagementFactRepository.findById(actionFactDTO.getRiskManagementUuid())
                            .orElseThrow(() -> new ResourceNotFoundException("Risk management fact not found with id: " +
                                    actionFactDTO.getRiskManagementUuid()));

                    ActionDim actionDim = actionDimRepository.findById(actionFactDTO.getActionDimUuid())
                            .orElseThrow(() -> new ResourceNotFoundException("Action dimension not found with id: " +
                                    actionFactDTO.getActionDimUuid()));

                    DateTimeDim dateTime = dateTimeDimRepository.findById(actionFactDTO.getDatetimeId())
                            .orElseThrow(() -> new ResourceNotFoundException("DateTime dimension not found with id: " +
                                    actionFactDTO.getDatetimeId()));

                    // Update entity with related entities
                    existingActionFact.setRiskManagement(riskManagement);
                    existingActionFact.setActionDim(actionDim);
                    existingActionFact.setDateTime(dateTime);

                    return convertToDTO(actionFactRepository.save(existingActionFact));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Action fact not found with id: " + uuid));
    }

    /**
     * Delete an action fact by UUID.
     *
     * @param uuid the action fact UUID
     * @throws ResourceNotFoundException if action fact not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!actionFactRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Action fact not found with id: " + uuid);
        }
        actionFactRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param actionFact the entity
     * @return the DTO
     */
    private ActionFactDTO convertToDTO(ActionFact actionFact) {
        ActionFactDTO dto = new ActionFactDTO();
        dto.setActionFactUuid(actionFact.getActionFactUuid());

        // Extract UUIDs from related entities
        if (actionFact.getRiskManagement() != null) {
            dto.setRiskManagementUuid(actionFact.getRiskManagement().getRiskManagementUuid());
        }

        if (actionFact.getActionDim() != null) {
            dto.setActionDimUuid(actionFact.getActionDim().getActionDimUuid());
        }

        if (actionFact.getDateTime() != null) {
            dto.setDatetimeId(actionFact.getDateTime().getDatetimeId());
        }

        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param actionFactDTO the DTO
     * @return the entity
     */
    private ActionFact convertToEntity(ActionFactDTO actionFactDTO) {
        ActionFact entity = new ActionFact();
        entity.setActionFactUuid(actionFactDTO.getActionFactUuid());

        // Set related entities if UUIDs are provided
        if (actionFactDTO.getRiskManagementUuid() != null) {
            RiskManagementFact riskManagement = riskManagementFactRepository.findById(actionFactDTO.getRiskManagementUuid())
                    .orElseThrow(() -> new ResourceNotFoundException("Risk management fact not found with id: " +
                            actionFactDTO.getRiskManagementUuid()));
            entity.setRiskManagement(riskManagement);
        }

        if (actionFactDTO.getActionDimUuid() != null) {
            ActionDim actionDim = actionDimRepository.findById(actionFactDTO.getActionDimUuid())
                    .orElseThrow(() -> new ResourceNotFoundException("Action dimension not found with id: " +
                            actionFactDTO.getActionDimUuid()));
            entity.setActionDim(actionDim);
        }

        if (actionFactDTO.getDatetimeId() != null) {
            DateTimeDim dateTime = dateTimeDimRepository.findById(actionFactDTO.getDatetimeId())
                    .orElseThrow(() -> new ResourceNotFoundException("DateTime dimension not found with id: " +
                            actionFactDTO.getDatetimeId()));
            entity.setDateTime(dateTime);
        }

        return entity;
    }
}
