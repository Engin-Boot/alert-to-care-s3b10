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
import com.philips.alerttocare.model.HealthMonitor;
import com.philips.alerttocare.repository.HealthMonitorRepository;

@RestController
@RequestMapping("/api/alerttocare/")
public class HealthMonitorController {

	@Autowired
	private HealthMonitorRepository healthMonitorRepository;
	
	// get monitors
	@GetMapping("monitors")
	public List<HealthMonitor> getAllMonitors() {
		return this.healthMonitorRepository.findAll();
	}
	
	// get monitor by id
	@GetMapping("monitors/{id}")
	public ResponseEntity<HealthMonitor> getMonitorById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		HealthMonitor monitor = healthMonitorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Monitor record not found for this id ::" + id));
		return ResponseEntity.ok().body(monitor);
	}

	// save monitor record
	@PostMapping("monitors")
	public HealthMonitor createMonitor(@RequestBody HealthMonitor monitor) {
		return this.healthMonitorRepository.save(monitor);
	}
	
	// update monitor record
	@PutMapping("monitors/{id}")
	public ResponseEntity<HealthMonitor> updateMonitor(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody HealthMonitor monitorRecord) throws ResourceNotFoundException {
		HealthMonitor monitor = healthMonitorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Monitor record not found for this id ::" + id));
		
		monitor.setLabel(monitorRecord.getLabel());		
		return ResponseEntity.ok(this.healthMonitorRepository.save(monitor));
	}
	
	// delete monitor record
	@DeleteMapping("monitors/{id}")
	public Map<String, Boolean> deleteMonitor(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		Map<String, Boolean> response = new HashMap<>();
		HealthMonitor monitor = healthMonitorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Monitor record not found for this id ::" + id));
		
		this.healthMonitorRepository.delete(monitor);
		response.put("Deleted", Boolean.TRUE);
		return response;
	}
}
