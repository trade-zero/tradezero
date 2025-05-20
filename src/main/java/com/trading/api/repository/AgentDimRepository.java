package com.trading.api.repository;

import com.trading.api.model.AgentDim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for AgentDim entity.
 */
@Repository
public interface AgentDimRepository extends JpaRepository<AgentDim, UUID> {
    
    /**
     * Find an agent by its name.
     *
     * @param name the name to search for
     * @return an Optional containing the agent if found
     */
    Optional<AgentDim> findByName(String name);
}
