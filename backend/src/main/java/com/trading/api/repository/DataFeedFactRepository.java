package com.trading.api.repository;

import com.trading.api.model.DataFeedFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for DataFeedFact entity.
 */
@Repository
public interface DataFeedFactRepository extends JpaRepository<DataFeedFact, UUID> {
    
    /**
     * Find a data feed by its name.
     *
     * @param name the name to search for
     * @return the data feed if found
     */
    DataFeedFact findByName(String name);
}
