package com.philips.alerttocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philips.alerttocare.model.HealthStatus;

@Repository
public interface HealthStatusRepository extends JpaRepository<HealthStatus, Long>{

}
