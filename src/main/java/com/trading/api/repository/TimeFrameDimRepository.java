package com.trading.api.repository;

import com.trading.api.model.TimeFrameDim;
import com.trading.api.model.enums.TradeTimeFrameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for TimeFrameDim entity.
 */
@Repository
public interface TimeFrameDimRepository extends JpaRepository<TimeFrameDim, TradeTimeFrameType> {
}
