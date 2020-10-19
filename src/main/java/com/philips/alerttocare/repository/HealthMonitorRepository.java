package com.philips.alerttocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philips.alerttocare.model.HealthMonitor;


@Repository
public interface HealthMonitorRepository extends JpaRepository<HealthMonitor, Long>{

}
