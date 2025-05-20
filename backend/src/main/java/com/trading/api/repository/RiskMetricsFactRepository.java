package com.trading.api.repository;

import com.trading.api.model.RiskMetricsFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for RiskMetricsFact entity.
 */
@Repository
public interface RiskMetricsFactRepository extends JpaRepository<RiskMetricsFact, UUID> {
    
    /**
     * Find all risk metrics by risk management UUID.
     *
     * @param riskManagementUuid the risk management UUID
     * @return a list of risk metrics
     */
    List<RiskMetricsFact> findByRiskManagement_RiskManagementUuid(UUID riskManagementUuid);
    
    /**
     * Find all risk metrics by datetime ID.
     *
     * @param datetimeId the datetime ID
     * @return a list of risk metrics
     */
    List<RiskMetricsFact> findByDateTime_DatetimeId(Long datetimeId);
    
    /**
     * Find risk metrics by risk management UUID and datetime ID.
     *
     * @param riskManagementUuid the risk management UUID
     * @param datetimeId the datetime ID
     * @return an Optional containing the risk metrics if found
     */
    Optional<RiskMetricsFact> findByRiskManagement_RiskManagementUuidAndDateTime_DatetimeId(
            UUID riskManagementUuid, Long datetimeId);
    
    /**
     * Find all risk metrics by margin used greater than the specified value.
     *
     * @param marginUsed the minimum margin used
     * @return a list of risk metrics
     */
    List<RiskMetricsFact> findByMarginUsedGreaterThan(Double marginUsed);
    
    /**
     * Find all risk metrics by max drawdown less than the specified value.
     *
     * @param maxDrawdown the maximum drawdown
     * @return a list of risk metrics
     */
    List<RiskMetricsFact> findByMaxDrawdownLessThan(Double maxDrawdown);
    
    /**
     * Find all risk metrics by sharpe ratio greater than the specified value.
     *
     * @param sharpeRatio the minimum sharpe ratio
     * @return a list of risk metrics
     */
    List<RiskMetricsFact> findBySharpeRatioGreaterThan(Double sharpeRatio);
}
