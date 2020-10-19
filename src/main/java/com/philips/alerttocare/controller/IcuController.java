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
import com.philips.alerttocare.model.Icu;
import com.philips.alerttocare.repository.IcuRepository;

@RestController
@RequestMapping("/api/alerttocare/")
public class IcuController {
	@Autowired
	private IcuRepository icuRepository;
	
	// get icu
	@GetMapping("icus")
	public List<Icu> getAllIcus() {
		return this.icuRepository.findAll();
	}
	
	// get icu by id
	@GetMapping("icus/{id}")
	public ResponseEntity<Icu> getIcuById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		Icu icu = icuRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ICU record not found for this id ::" + id));
		return ResponseEntity.ok().body(icu);
	}

	// save icu record
	@PostMapping("icus")
	public Icu createIcu(@RequestBody Icu icu) {
		return this.icuRepository.save(icu);
	}
	
	// update icu record
	@PutMapping("icus/{id}")
	public ResponseEntity<Icu> updateIcu(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody Icu icuRecord) throws ResourceNotFoundException {
		Icu icu = icuRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ICU record not found for this id ::" + id));
		
		icu.setLabel(icuRecord.getLabel());
		
		return ResponseEntity.ok(this.icuRepository.save(icu));
	}
	
	// delete icu record
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
