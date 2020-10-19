package com.philips.alerttocare.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "HealthStatus")
/**
 * This HealthStatus class is an entity with 
 * id , and keeps track of patient vital science
 * and Occupancy , monitor object with their id's as foreign key 
 **/
public class HealthStatus implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "Heart Rate")
	private Double heartrate;
	
	@Column(name = "BP")
	private Double bp;
	
	@Column(name = "SPO2")
	private Double spo2;
	
	@Column(name = "Respiratory Rate")
	private Double respiratoryrate;
	
	@Column(name = "Created At")
	private Date createdAt;
	
	@ManyToOne
    @JoinColumn(referencedColumnName="Id")
	private Occupancy occupancy;

	@ManyToOne
    @JoinColumn(referencedColumnName="Id")
	private HealthMonitor monitor;
	
	public HealthStatus() {
		super();
	}

	public HealthStatus(Double heartrate, Double bp, Double spo2, Double respiratoryrate, Occupancy occupancy,
			HealthMonitor monitor) {
		super();
		this.heartrate = heartrate;
		this.bp = bp;
		this.spo2 = spo2;
		this.respiratoryrate = respiratoryrate;
		this.occupancy = occupancy;
		this.monitor = monitor;
	}

	public Long getId() {
		return id;
	}

	public Double getHeartrate() {
		return heartrate;
	}

	public void setHeartrate(Double heartrate) {
		this.heartrate = heartrate;
	}

	public Double getBp() {
		return bp;
	}

	public void setBp(Double bp) {
		this.bp = bp;
	}

	public Double getSpo2() {
		return spo2;
	}

	public void setSpo2(Double spo2) {
		this.spo2 = spo2;
	}

	public Double getRespiratoryrate() {
		return respiratoryrate;
	}

	public void setRespiratoryrate(Double respiratoryrate) {
		this.respiratoryrate = respiratoryrate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	@PrePersist
	public void setCreatedAt() {
		this.createdAt = new Date();
	}
	
	public Occupancy getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(Occupancy occupancy) {
		this.occupancy = occupancy;
	}

	public HealthMonitor getMonitor() {
		return monitor;
	}

	public void setMonitor(HealthMonitor monitor) {
		this.monitor = monitor;
	}

}
