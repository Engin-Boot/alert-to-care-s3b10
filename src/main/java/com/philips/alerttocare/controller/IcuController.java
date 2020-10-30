package com.philips.alerttocare.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.philips.alerttocare.model.Bed;
import com.philips.alerttocare.model.HealthMonitor;
import com.philips.alerttocare.repository.BedRepository;
import com.philips.alerttocare.repository.HealthMonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.philips.alerttocare.exception.ResourceNotFoundException;
import com.philips.alerttocare.model.Icu;
import com.philips.alerttocare.repository.IcuRepository;

@RestController
@RequestMapping("/api/alerttocare/")
public class IcuController {
	@Autowired
	private IcuRepository icuRepository;

	@Autowired
	private BedRepository bedRepository;

	@Autowired
	private HealthMonitorRepository healthMonitorRepository;
	
	// get icu
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("icus")
	public List<Icu> getAllIcus() {
		return this.icuRepository.findAll();
	}
	
	// get icu by id
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("icus/{id}")
	public ResponseEntity<Icu> getIcuById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		Icu icu = icuRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ICU record not found for this id ::" + id));
		return ResponseEntity.ok().body(icu);
	}

	// save icu record
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("icus")
	public Icu createIcu(@RequestBody Icu icu) {
		icuRepository.save(icu);
		for(int i=0;i<icu.getBedCount();i++){
			long bedId=icu.getId()*1000+i+1;
			System.out.println(bedId);
			Bed bed = new Bed(bedId, "", false, false, icu );
			bedRepository.save(bed);
			long healthMonitorId = icu.getId()*1000+i+1;//generate random
			HealthMonitor healthMonitor= new HealthMonitor(healthMonitorId,null,null,null,bed);
			healthMonitorRepository.save(healthMonitor);
		}
		return icu;
	}
	
	// update icu record
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("icus/{id}")
	public ResponseEntity<Icu> updateIcu(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody Icu icuRecord) throws ResourceNotFoundException {
		Icu icu = icuRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ICU record not found for this id ::" + id));
		
		icu.setLabel(icuRecord.getLabel());
		
		return ResponseEntity.ok(this.icuRepository.save(icu));
	}
	
	// delete icu record
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("icus/{id}")
	public Map<String, Boolean> deleteIcu(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		Map<String, Boolean> response = new HashMap<>();
		Icu icu = icuRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ICU record not found for this id ::" + id));
		this.icuRepository.delete(icu);
		response.put("Deleted", Boolean.TRUE);
		return response;
	}
}
