package com.philips.alerttocare.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.philips.alerttocare.model.Bed;
import com.philips.alerttocare.model.HealthMonitor;
import com.philips.alerttocare.model.Occupancy;
import com.philips.alerttocare.repository.BedRepository;
import com.philips.alerttocare.repository.HealthMonitorRepository;
import com.philips.alerttocare.repository.OccupancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.philips.alerttocare.exception.ResourceNotFoundException;
import com.philips.alerttocare.model.Patient;
import com.philips.alerttocare.repository.PatientRepository;

@RestController
@RequestMapping("/api/alerttocare/")
public class PatientController {
	
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private OccupancyRepository occupancyRepository;
	@Autowired
	private BedRepository bedRepository;
	@Autowired
	private HealthMonitorRepository healthMonitorRepository;
	// get patients
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("patients")
	public List<Patient> getAllPatients() {
		return this.patientRepository.findAll();
	}
	
	// get patient by id
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("patients/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Patient record not found for this id ::" + id));
		return ResponseEntity.ok().body(patient);
	}

	// save patient record
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("patients")
	public Patient createPatient(@RequestBody Patient patient) {
		return this.patientRepository.save(patient);
	}
	
	// update occupancy record
	@CrossOrigin(origins = "http://localhost:4200")
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
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("patients/{id}")
	public Map<String, Boolean> deletePatient(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		Map<String, Boolean> response = new HashMap<>();
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Patient record not found for this id ::" + id));
		Occupancy occupancy = occupancyRepository.findByPatient(patient);

		Bed bed = occupancy.getBed();
		bed.setOccupiedFlag(false);
		System.out.println("85-----"+bed.getId());

		HealthMonitor healthMonitor = healthMonitorRepository.findByBed(bed);
		System.out.println(occupancy.getId());
		healthMonitor.setBp(null);
		healthMonitor.setSpo2(null);
		healthMonitor.setRespiratoryrate(null);
		System.out.println(healthMonitor.getId());
		healthMonitorRepository.save(healthMonitor);

		bedRepository.save(bed);
		occupancyRepository.delete(occupancy);
		this.patientRepository.delete(patient);
		response.put("Deleted", Boolean.TRUE);
		return response;
	}
		
}
