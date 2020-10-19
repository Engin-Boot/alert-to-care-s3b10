package com.philips.alerttocare.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.philips.alerttocare.exception.ResourceNotFoundException;
import com.philips.alerttocare.model.StaffDetails;
import com.philips.alerttocare.repository.StaffDetailsRepository;

@RestController
@RequestMapping("/api/alerttocare/")
public class StaffDetailsController {

	@Autowired
	private StaffDetailsRepository staffDetailsRepository;
	
	// get staff details
	@GetMapping("staffdetails")
	public List<StaffDetails> getAllStaffDetails() {
		return this.staffDetailsRepository.findAll();
	}
	
	// get staff details by user id
	@GetMapping("staffdetails/{id}")
	public ResponseEntity<StaffDetails> getStaffDetailsByUsername(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		StaffDetails staffDetails = staffDetailsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Staff details record not found for this id ::" + id));
		return ResponseEntity.ok().body(staffDetails);
	}

	// save staff details record
	@PostMapping("staffdetails")
	public StaffDetails createStaffDetails(@RequestBody StaffDetails staffDetails) {
		return this.staffDetailsRepository.save(staffDetails);
	}
	
	// update staff details record by id
	@PutMapping("staffdetails/{id}")
	public ResponseEntity<StaffDetails> updateStaffDetailsByStaffId(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody StaffDetails staffDetailsRecord) throws ResourceNotFoundException {
		StaffDetails staffDetails = staffDetailsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Staff details record not found for this id ::" + id));
		
		staffDetails.setAdminPrevilige(staffDetailsRecord.isAdminPrevilige());
		staffDetails.setDesignation(staffDetailsRecord.getDesignation());
		staffDetails.setPassword(staffDetailsRecord.getPassword());
		staffDetails.setUsername(staffDetailsRecord.getUsername());
		
		return ResponseEntity.ok(this.staffDetailsRepository.save(staffDetails));
	}
	
	// delete staff details record by id
	@DeleteMapping("staffdetails/{id}")
	public Map<String, Boolean> deleteStaffDetailsByUserId(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		Map<String, Boolean> response = new HashMap<>();
		StaffDetails staffDetails = staffDetailsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Staff details record not found for this id ::" + id));
		
		this.staffDetailsRepository.delete(staffDetails);
		response.put("Deleted", Boolean.TRUE);
		return response;
	}	
	
}
