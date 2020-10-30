package com.philips.alerttocare.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.philips.alerttocare.model.HealthMonitor;
import com.philips.alerttocare.repository.HealthMonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.philips.alerttocare.exception.ResourceNotFoundException;
import com.philips.alerttocare.model.Bed;
import com.philips.alerttocare.repository.BedRepository;

@RestController
@RequestMapping("/api/alerttocare/")
public class BedController {
	
	@Autowired
	private BedRepository bedRepository;
	@Autowired
	private HealthMonitorRepository healthMonitorRepository;
	
	// get beds
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("beds")
	public List<Bed> getAllBeds() {
		return this.bedRepository.findAll();
	}
	
	// get bed by id
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("beds/{id}")
	public ResponseEntity<Bed> getBedById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		Bed bed = bedRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Bed record not found for this id ::" + id));
		return ResponseEntity.ok().body(bed);
	}

	// save bed record
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("beds")
	public Bed createBed(@RequestBody Bed bed) {
		return this.bedRepository.save(bed);
	}

	// change monitor for a bed
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("beds/monitor/{bedId}/{monitorid}")
	public Bed changeMonitorForABed(@PathVariable(value = "bedId") Long bedId, @PathVariable(value = "monitorid") Long newMonitorId)  {

		Optional<Bed> bed=bedRepository.findById(bedId);
		Optional<HealthMonitor> newMonitor=healthMonitorRepository.findById(newMonitorId);
		HealthMonitor oldMonitor = healthMonitorRepository.findByBed(bed.get());

		newMonitor.get().setbed(bed.get());
		healthMonitorRepository.save(newMonitor.get());

		oldMonitor.setbed(null);
		healthMonitorRepository.save(oldMonitor);

		return this.bedRepository.save(bed.get());
	}
	
	// update bed record
	@CrossOrigin(origins = "http://localhost:4200")
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
	@CrossOrigin(origins = "http://localhost:4200")
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
