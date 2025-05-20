package com.trading.api.service;

import com.trading.api.dto.ActionDimDTO;
import com.trading.api.exception.ResourceNotFoundException;
import com.trading.api.model.ActionDim;
import com.trading.api.model.enums.TradeActionType;
import com.trading.api.model.enums.TradeAssetType;
import com.trading.api.model.enums.TradeDirectionType;
import com.trading.api.repository.ActionDimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for ActionDim entity operations.
 */
@Service
@RequiredArgsConstructor
public class ActionDimService {

    private final ActionDimRepository actionDimRepository;

    /**
     * Get all actions.
     *
     * @return list of all actions
     */
    public List<ActionDimDTO> findAll() {
        return actionDimRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get action by UUID.
     *
     * @param uuid the action UUID
     * @return the action
     * @throws ResourceNotFoundException if action not found
     */
    public ActionDimDTO findById(UUID uuid) {
        return actionDimRepository.findById(uuid)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Action not found with id: " + uuid));
    }

    /**
     * Get actions by asset type.
     *
     * @param assetType the asset type
     * @return list of actions
     */
    public List<ActionDimDTO> findByAssetType(TradeAssetType assetType) {
        return actionDimRepository.findByAssetType(assetType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get actions by direction type.
     *
     * @param directionType the direction type
     * @return list of actions
     */
    public List<ActionDimDTO> findByDirectionType(TradeDirectionType directionType) {
        return actionDimRepository.findByDirectionType(directionType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get actions by action type.
     *
     * @param actionType the action type
     * @return list of actions
     */
    public List<ActionDimDTO> findByActionType(TradeActionType actionType) {
        return actionDimRepository.findByActionType(actionType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new action.
     *
     * @param actionDimDTO the action to create
     * @return the created action
     */
    @Transactional
    public ActionDimDTO create(ActionDimDTO actionDimDTO) {
        ActionDim actionDim = convertToEntity(actionDimDTO);
        actionDim.setActionDimUuid(null); // Ensure UUID is generated
        ActionDim savedActionDim = actionDimRepository.save(actionDim);
        return convertToDTO(savedActionDim);
    }

    /**
     * Update an existing action.
     *
     * @param uuid the action UUID
     * @param actionDimDTO the action data to update
     * @return the updated action
     * @throws ResourceNotFoundException if action not found
     */
    @Transactional
    public ActionDimDTO update(UUID uuid, ActionDimDTO actionDimDTO) {
        return actionDimRepository.findById(uuid)
                .map(existingAction -> {
                    existingAction.setAssetType(actionDimDTO.getAssetType());
                    existingAction.setDirectionType(actionDimDTO.getDirectionType());
                    existingAction.setActionType(actionDimDTO.getActionType());
                    existingAction.setVolume(actionDimDTO.getVolume());
                    return convertToDTO(actionDimRepository.save(existingAction));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Action not found with id: " + uuid));
    }

    /**
     * Delete an action by UUID.
     *
     * @param uuid the action UUID
     * @throws ResourceNotFoundException if action not found
     */
    @Transactional
    public void delete(UUID uuid) {
        if (!actionDimRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Action not found with id: " + uuid);
        }
        actionDimRepository.deleteById(uuid);
    }

    /**
     * Convert entity to DTO.
     *
     * @param actionDim the entity
     * @return the DTO
     */
    private ActionDimDTO convertToDTO(ActionDim actionDim) {
        ActionDimDTO dto = new ActionDimDTO();
        dto.setActionDimUuid(actionDim.getActionDimUuid());
        dto.setAssetType(actionDim.getAssetType());
        dto.setDirectionType(actionDim.getDirectionType());
        dto.setActionType(actionDim.getActionType());
        dto.setVolume(actionDim.getVolume());
        return dto;
    }

    /**
     * Convert DTO to entity.
     *
     * @param actionDimDTO the DTO
     * @return the entity
     */
    private ActionDim convertToEntity(ActionDimDTO actionDimDTO) {
        ActionDim entity = new ActionDim();
        entity.setActionDimUuid(actionDimDTO.getActionDimUuid());
        entity.setAssetType(actionDimDTO.getAssetType());
        entity.setDirectionType(actionDimDTO.getDirectionType());
        entity.setActionType(actionDimDTO.getActionType());
        entity.setVolume(actionDimDTO.getVolume());
        return entity;
    }
}
