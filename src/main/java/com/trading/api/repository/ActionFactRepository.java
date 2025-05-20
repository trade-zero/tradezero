package com.trading.api.repository;

import com.trading.api.model.ActionFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for ActionFact entity.
 */
@Repository
public interface ActionFactRepository extends JpaRepository<ActionFact, UUID> {
    
    /**
     * Find all action facts by risk management UUID.
     *
     * @param riskManagementUuid the risk management UUID
     * @return a list of action facts
     */
    List<ActionFact> findByRiskManagement_RiskManagementUuid(UUID riskManagementUuid);
    
    /**
     * Find all action facts by action dimension UUID.
     *
     * @param actionDimUuid the action dimension UUID
     * @return a list of action facts
     */
    List<ActionFact> findByActionDim_ActionDimUuid(UUID actionDimUuid);
    
    /**
     * Find all action facts by datetime ID.
     *
     * @param datetimeId the datetime ID
     * @return a list of action facts
     */
    List<ActionFact> findByDateTime_DatetimeId(Long datetimeId);
}
