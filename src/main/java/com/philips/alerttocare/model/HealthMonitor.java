package com.philips.alerttocare.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "HealthMonitors")
/**
 * This HealthMonitor class is an entity with 
 * id ,Monitor label it is to monitor the device
 **/
public class HealthMonitor implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "Monitor Label")
	private String label;
	
	@Column(name = "Created At")
	private Date createdAt;
	
	
	
	public HealthMonitor() {
		super();
	}
	
	public HealthMonitor(String label) {
		super();
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	@PrePersist
	public void setCreatedAt() {
		this.createdAt = new Date();
	}

	
	public Long getId() {
		return id;
	}

}
