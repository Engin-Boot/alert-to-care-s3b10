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
import com.philips.alerttocare.model.Occupancy;
import com.philips.alerttocare.repository.OccupancyRepository;

@RestController
@RequestMapping("/api/alerttocare/")
public class OccupancyController {
	
	@Autowired
	private OccupancyRepository occupancyRepository;
	
	// get occupancies
	@GetMapping("occupancies")
	public List<Occupancy> getAllOccupancies() {
		return this.occupancyRepository.findAll();
	}
	
	// get occupancy by id
	@GetMapping("occupancies/{id}")
	public ResponseEntity<Occupancy> getOccupancyById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		Occupancy occupancy = occupancyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Occupancy record not found for this id ::" + id));
		return ResponseEntity.ok().body(occupancy);
	}
	

	// save occupancy record
	@PostMapping("occupancies")
	public Occupancy createOccupancies(@RequestBody Occupancy occupancy) {
		return this.occupancyRepository.save(occupancy);
	}
	
	// update occupancy record
	@PutMapping("occupancies/{id}")
	public ResponseEntity<Occupancy> updateOccupancy(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody Occupancy occupancyrecord) throws ResourceNotFoundException {
		Occupancy occupancy = occupancyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Occupancy record not found for this id ::" + id));
		
		occupancy.setDischargedAt(occupancyrecord.getDischargedAt());
		occupancy.setIcu(occupancyrecord.getIcu());
		occupancy.setBed(occupancyrecord.getBed());
		occupancy.setPatient(occupancyrecord.getPatient());
		
		return ResponseEntity.ok(this.occupancyRepository.save(occupancy));
	}
	
	// delete status record
	@DeleteMapping("occupancies/{id}")
	public Map<String, Boolean> deleteStatus(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		Map<String, Boolean> response = new HashMap<>();
		Occupancy occupancy = occupancyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Occupancy record not found for this id ::" + id));
		
		this.occupancyRepository.delete(occupancy);
		response.put("Deleted", Boolean.TRUE);
		return response;
	}
	
}
