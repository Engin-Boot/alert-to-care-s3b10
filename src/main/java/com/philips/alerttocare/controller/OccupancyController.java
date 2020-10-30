package com.philips.alerttocare.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.philips.alerttocare.model.Bed;
import com.philips.alerttocare.repository.BedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.philips.alerttocare.exception.ResourceNotFoundException;
import com.philips.alerttocare.model.Occupancy;
import com.philips.alerttocare.repository.OccupancyRepository;

@RestController
@RequestMapping("/api/alerttocare/")
public class OccupancyController {
	
	@Autowired
	private OccupancyRepository occupancyRepository;

	@Autowired
	private BedRepository bedRepository;
	
	// get occupancies
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("occupancies")
	public List<Occupancy> getAllOccupancies() {
		return this.occupancyRepository.findAll();
	}
	
	// get occupancy by id
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("occupancies/{id}")
	public ResponseEntity<Occupancy> getOccupancyById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		Occupancy occupancy = occupancyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Occupancy record not found for this id ::" + id));
		return ResponseEntity.ok().body(occupancy);
	}
	

	// save occupancy record
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("occupancies")
	public Occupancy createOccupancies(@RequestBody Occupancy occupancy) {
		Bed bed = occupancy.getBed();
		bed.setOccupiedFlag(true);
		bedRepository.save(bed);
		return this.occupancyRepository.save(occupancy);
	}
	
	// update occupancy record
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("occupancies/{id}")
	public ResponseEntity<Occupancy> updateOccupancy(@PathVariable(value = "id") Long id, @Valid @RequestBody Occupancy occupancyrecord)
			throws ResourceNotFoundException {
		Occupancy occupancy = occupancyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Occupancy record not found for this id ::" + id));
		
		occupancy.setDischargedAt(occupancyrecord.getDischargedAt());
		occupancy.setIcu(occupancyrecord.getIcu());
		occupancy.setBed(occupancyrecord.getBed());
		occupancy.setPatient(occupancyrecord.getPatient());
		
		return ResponseEntity.ok(this.occupancyRepository.save(occupancy));
	}
	
	// delete status record
	@CrossOrigin(origins = "http://localhost:4200")
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
