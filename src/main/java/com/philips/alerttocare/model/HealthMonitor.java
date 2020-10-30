package com.philips.alerttocare.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "HealthMonitors")
/**
 * This HealthMonitor class is an entity with 
 * id ,Monitor label it is to monitor the device
 **/
public class HealthMonitor implements Serializable {


	@Id
	@Column(name = "Id")
	private Long id;

	@Column(name = "BP")
	private Double bp;

	@Column(name = "SPO2")
	private Double spo2;

	@Column(name = "Respiratory Rate")
	private Double respiratoryrate;

	@Column(name = "Created At")
	private Date createdAt;

	@OneToOne
	@JoinColumn(referencedColumnName="Id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Bed bed;

	public HealthMonitor() {
		super();
	}
	
	public HealthMonitor(Long id,Double bp,Double spo2, Double respiratoryrate,Bed bed) {
		super();
		this.id = id;
		this.bp= bp;
		this.spo2=spo2;
		this.respiratoryrate=respiratoryrate;
		this.bed = bed;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) { this.id= id; }

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

	public Bed getBed() {
		return bed;
	}
	public void setbed(Bed bed) { this.bed= bed; }

	public Date getCreatedAt() {
		return createdAt;
	}

	@PrePersist
	public void setCreatedAt() {
		this.createdAt = new Date();
	}

	


}
