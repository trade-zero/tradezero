package com.trading.api.repository;

import com.trading.api.model.DateTimeDim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for DateTimeDim entity.
 */
@Repository
public interface DateTimeDimRepository extends JpaRepository<DateTimeDim, Long> {
}
