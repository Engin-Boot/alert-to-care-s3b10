package com.philips.alerttocare.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.philips.alerttocare.model.Bed;
import com.philips.alerttocare.repository.BedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.philips.alerttocare.exception.ResourceNotFoundException;
import com.philips.alerttocare.model.HealthMonitor;
import com.philips.alerttocare.repository.HealthMonitorRepository;

@RestController
@RequestMapping("/api/alerttocare/")
public class HealthMonitorController {

	@Autowired
	private HealthMonitorRepository healthMonitorRepository;
	@Autowired
	private BedRepository bedRepository;
	
	// get monitors
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("monitors")
	public List<HealthMonitor> getAllMonitors() {
		return this.healthMonitorRepository.findAll();
	}
	
	// get monitor by id
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("monitors/{id}")
	public ResponseEntity<HealthMonitor> getMonitorById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		HealthMonitor monitor = healthMonitorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Monitor record not found for this id ::" + id));
		return ResponseEntity.ok().body(monitor);
	}

	// save monitor record
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("monitors")
	public HealthMonitor createMonitor(@RequestBody HealthMonitor monitor) {
		return this.healthMonitorRepository.save(monitor);
	}
	
	// update monitor record
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("monitors/{id}")
	public ResponseEntity<HealthMonitor> updateMonitor(@PathVariable(value = "id") Long id, 
			@Valid @RequestBody HealthMonitor monitorRecord) throws ResourceNotFoundException {
		HealthMonitor monitor = healthMonitorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Monitor record not found for this id ::" + id));

		double respRate = monitorRecord.getRespiratoryrate();
		double spo2 = monitorRecord.getSpo2();
		double bp = monitorRecord.getBp();

		monitor.setRespiratoryrate(respRate);
		monitor.setSpo2(spo2);
		monitor.setBp(bp);
		healthMonitorRepository.save(monitor);

		Bed bed=monitor.getBed();

		boolean BP =  isBpOk(bp, bed, monitor);
		boolean Spo2 = isSpo2Ok(spo2, bed, monitor);
		boolean RespRate = isRespRateOk(respRate, bed, monitor);

		System.out.println("BP"+BP);
		System.out.println("Spo2"+Spo2);
		System.out.println("Spo2"+Spo2);


		if(BP || Spo2 || RespRate)
		{
			bed.setAlertstatus(true);
			System.out.println("True");
		}
		else
		{
			bed.setAlertstatus(false);
			System.out.println("False");
		}

		bedRepository.save(bed);
		return ResponseEntity.ok(this.healthMonitorRepository.save(monitor));
	}

	public Boolean isBpOk(double bp,Bed bed,HealthMonitor healthMonitor)
	{
		if(bp<70)
		{	bed.setBpStatus("BP is low-->"+healthMonitor.getBp());
			return false;}
        else if(bp > 150)
		{	bed.setBpStatus("BP is high-->"+healthMonitor.getBp());
        	return false;}
		else
			bed.setBpStatus("BP is Normal-->"+healthMonitor.getBp());
			return true;
	}

	public Boolean isRespRateOk(double respRate,Bed bed,HealthMonitor healthMonitor)
	{
		if(respRate<70)
		{
			bed.setRespRateStatus("Respiratory rate is low-->"+healthMonitor.getRespiratoryrate());
			return false;
		}
		else if(respRate > 150){
			bed.setRespRateStatus("Respiratory rate is high-->"+healthMonitor.getRespiratoryrate());
			return false;
		}
		else
			bed.setRespRateStatus("Respiratory rate is normal-->"+healthMonitor.getRespiratoryrate());
			return true;
	}

	public Boolean isSpo2Ok(double spo2,Bed bed,HealthMonitor healthMonitor)
	{

		if(spo2<70){
			bed.setSpo2Status("Spo2 is low-->"+healthMonitor.getSpo2());
			return false;
		}

		else if(spo2 > 150) {
			bed.setSpo2Status("Spo2 is high-->" + healthMonitor.getSpo2());
			return false;
		}
		else
			bed.setSpo2Status("Spo2 is normal-->" + healthMonitor.getSpo2());
		return true;
	}

	// delete monitor record
	@CrossOrigin(origins = "http://localhost:4200")
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
