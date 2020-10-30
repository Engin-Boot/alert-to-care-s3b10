package com.philips.alerttocare.repository;

import com.philips.alerttocare.model.Bed;
import com.philips.alerttocare.model.Occupancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philips.alerttocare.model.HealthMonitor;

import javax.persistence.criteria.CriteriaBuilder;


@Repository
public interface HealthMonitorRepository extends JpaRepository<HealthMonitor, Long>{
    HealthMonitor findByBed(Bed bed);
}
