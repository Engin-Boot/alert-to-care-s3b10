package com.philips.alerttocare.model;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
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

	@Id
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

	@Column(name = "BP Status")
	private String bpStatus;
	@Column(name = "Spo2 Status")
	private String spo2Status;
	@Column(name = "Respiratory Rate Status")
	private String respRateStatus;

	@ManyToOne
	@JoinColumn(referencedColumnName="Id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Icu icu;

	public Bed() {
		super();
	}
	public Bed(Long id,String label, boolean occupiedFlag, boolean alertstatus, Icu icu) {
		super();
		this.id= id;
		this.label = label;
		this.occupiedFlag = occupiedFlag;
		this.alertstatus = alertstatus;
		this.icu = icu;
	}

	public void setId(Long id){
		this.id=id;
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
	public void setBpStatus(String bpStatus)	{
		this.bpStatus=bpStatus;
	}
	public String getBpStatus() {
		return bpStatus;
	}
	public void setSpo2Status(String spo2Status)	{
		this.spo2Status=spo2Status;
	}
	public String getSpo2Status() {
		return spo2Status;
	}
	public void setRespRateStatus(String respRateStatus){
		this.respRateStatus=respRateStatus;
	}
	public String getRespRateStatus(){
		return respRateStatus;
	}
}