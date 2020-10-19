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
import com.philips.alerttocare.model.Patient;
import com.philips.alerttocare.repository.PatientRepository;

@RestController
@RequestMapping("/api/alerttocare/")
public class PatientController {
	
	@Autowired
	private PatientRepository patientRepository;
	
	// get patients
	@GetMapping("patients")
	public List<Patient> getAllPatients() {
		return this.patientRepository.findAll();
	}
	
	// get patient by id
	@GetMapping("patients/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Patient record not found for this id ::" + id));
		return ResponseEntity.ok().body(patient);
	}

	// save patient record
	@PostMapping("patients")
	public Patient createPatient(@RequestBody Patient patient) {
		return this.patientRepository.save(patient);
	}
	
	// update occupancy record
	@PutMapping("patients/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody Patient patientrecord) throws ResourceNotFoundException {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Patient record not found for this id ::" + id));
		
		patient.setAddress(patientrecord.getAddress());
		patient.setAge(patientrecord.getAge());
		patient.setContact(patientrecord.getContact());
		patient.setGender(patientrecord.getGender());
		patient.setName(patientrecord.getName());
		patient.setStaffdetails(patientrecord.getStaffdetails());
		
		return ResponseEntity.ok(this.patientRepository.save(patient));
	}
	
	// delete status record
	@DeleteMapping("patients/{id}")
	public Map<String, Boolean> deletePatient(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		Map<String, Boolean> response = new HashMap<>();
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Patient record not found for this id ::" + id));
		
		this.patientRepository.delete(patient);
		response.put("Deleted", Boolean.TRUE);
		return response;
	}
		
}
