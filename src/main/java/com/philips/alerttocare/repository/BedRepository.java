package com.philips.alerttocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philips.alerttocare.model.Bed;

@Repository
public interface BedRepository extends JpaRepository<Bed, Long>{

}
