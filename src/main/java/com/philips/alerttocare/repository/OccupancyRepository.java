package com.philips.alerttocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philips.alerttocare.model.Occupancy;

@Repository
public interface OccupancyRepository extends JpaRepository<Occupancy, Long> {

}