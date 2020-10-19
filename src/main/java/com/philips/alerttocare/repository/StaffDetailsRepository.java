package com.philips.alerttocare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.philips.alerttocare.model.StaffDetails;

@Repository
public interface StaffDetailsRepository extends JpaRepository<StaffDetails, Long>{
	
	List<StaffDetails> findByDesignationContaining(String designation);
}
