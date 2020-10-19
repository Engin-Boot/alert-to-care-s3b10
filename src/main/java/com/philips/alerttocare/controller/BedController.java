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
import com.philips.alerttocare.model.Bed;
import com.philips.alerttocare.repository.BedRepository;

@RestController
@RequestMapping("/api/alerttocare/")
public class BedController {
	
	@Autowired
	private BedRepository bedRepository;
	
	// get beds
	@GetMapping("beds")
	public List<Bed> getAllBeds() {
		return this.bedRepository.findAll();
	}
	
	// get bed by id
	@GetMapping("beds/{id}")
	public ResponseEntity<Bed> getBedById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		Bed bed = bedRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Bed record not found for this id ::" + id));
		return ResponseEntity.ok().body(bed);
	}

	// save bed record
	@PostMapping("beds")
	public Bed createBed(@RequestBody Bed bed) {
		return this.bedRepository.save(bed);
	}
	
	// update bed record
	@PutMapping("beds/{id}")
	public ResponseEntity<Bed> updateBed(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody Bed bedRecord) throws ResourceNotFoundException {
		Bed bed = bedRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Bed record not found for this id ::" + id));
		
		bed.setLabel(bedRecord.getLabel());
		bed.setOccupiedFlag(bedRecord.getOccupiedFlag());
		bed.setAlertstatus(bedRecord.isAlertstatus());
		bed.setIcu(bedRecord.getIcu());
		
		return ResponseEntity.ok(this.bedRepository.save(bed));
	}
	
	// delete bed record
	@DeleteMapping("beds/{id}")
	public Map<String, Boolean> deleteBed(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		Map<String, Boolean> response = new HashMap<>();
		Bed bed = bedRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Bed record not found for this id ::" + id));
		this.bedRepository.delete(bed);
		response.put("Deleted", Boolean.TRUE);
		return response;
	}
}
