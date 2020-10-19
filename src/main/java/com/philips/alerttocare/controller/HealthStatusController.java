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
import com.philips.alerttocare.model.HealthStatus;
import com.philips.alerttocare.repository.HealthStatusRepository;

@RestController
@RequestMapping("/api/alerttocare/")
public class HealthStatusController {
	
	@Autowired
	private HealthStatusRepository healthStatusRepository;
	
	// get status
	@GetMapping("healthstatus")
	public List<HealthStatus> getAllStatus() {
		return this.healthStatusRepository.findAll();
	}
	
	// get status by id
	@GetMapping("healthstatus/{id}")
	public ResponseEntity<HealthStatus> getStatusById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		HealthStatus healthStatus = healthStatusRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Health status record not found for this id ::" + id));
		return ResponseEntity.ok().body(healthStatus);
	}

	// save status record
	@PostMapping("healthstatus")
	public HealthStatus createStatus(@RequestBody HealthStatus healthStatus) {
		return this.healthStatusRepository.save(healthStatus);
	}
	
	// update status record
	@PutMapping("healthstatus/{id}")
	public ResponseEntity<HealthStatus> updateStatus(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody HealthStatus healthstatusRecord) throws ResourceNotFoundException {
		HealthStatus healthstatus = healthStatusRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Health status record not found for this id ::" + id));
		
		healthstatus.setBp(healthstatusRecord.getBp());
		healthstatus.setHeartrate(healthstatusRecord.getHeartrate());
		healthstatus.setRespiratoryrate(healthstatusRecord.getRespiratoryrate());
		healthstatus.setSpo2(healthstatusRecord.getSpo2());
		healthstatus.setMonitor(healthstatusRecord.getMonitor());
		healthstatus.setOccupancy(healthstatusRecord.getOccupancy());
		
		return ResponseEntity.ok(this.healthStatusRepository.save(healthstatus));
	}
	
	// delete status record
	@DeleteMapping("healthstatus/{id}")
	public Map<String, Boolean> deleteStatus(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		Map<String, Boolean> response = new HashMap<>();
		HealthStatus healthstatus = healthStatusRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Health status record not found for this id ::" + id));
		
		this.healthStatusRepository.delete(healthstatus);
		response.put("Deleted", Boolean.TRUE);
		return response;
	}
	
}
