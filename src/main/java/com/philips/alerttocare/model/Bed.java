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
@Table(name = "Beds")
/**
 * This Bed class is an entity with 
 * id , label , occupied flag and alertstatus
 * and Icu object with its id as foreign key 
 **/
public class Bed implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "Bed Label")
	private String label;
	
	@Column(name = "Occupied")
	private boolean occupiedFlag;
	
	@Column(name = "Alert Status")
	private boolean alertstatus;
	
	@Column(name = "Created At")
	private Date createdAt;
	

	
	@ManyToOne
	@JoinColumn(referencedColumnName="Id")
	private Icu icu;
	
	public Bed() {
		super();
	}

	public Bed(String label, boolean occupiedFlag, boolean alertstatus, Icu icu) {
		super();
		this.label = label;
		this.occupiedFlag = occupiedFlag;
		this.alertstatus = alertstatus;
		this.icu = icu;
	}

	public Long getId() {
		return id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean getOccupiedFlag() {
		return occupiedFlag;
	}
	public void setOccupiedFlag(boolean occupiedFlag) {
		this.occupiedFlag = occupiedFlag;
	}

	public Icu getIcu() {
		return icu;
	}

	public void setIcu(Icu icu) {
		this.icu = icu;
	}

	@PrePersist
	public void createdAt() {
	    this.createdAt = new Date();
	}
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public boolean isAlertstatus() {
		return alertstatus;
	}

	public void setAlertstatus(boolean alertstatus) {
		this.alertstatus = alertstatus;
	}
	
}
