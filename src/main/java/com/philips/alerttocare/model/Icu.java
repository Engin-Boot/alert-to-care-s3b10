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
@Table(name = "ICUs")
/**
 * This Icu class is an entity with
 * id , Icu label and
 * it has list of beds contained in particular icu
 **/
public class Icu implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name = "ICU Label")
	private String label;
	@Column(name = "BedCount")
	private Integer bedCount;

	@Column(name = "Created At")
	private Date createdAt;

	public Icu() {
		super();
	}
	public Icu(String label,Integer bedCount) {
		super();
		this.label = label;
		this.bedCount=bedCount;
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
	public void setBedCount(Integer bedCount) {
		this.bedCount = bedCount;
	}
	public Integer getBedCount() {
		return bedCount;
	}

	@PrePersist
	public void setCreatedAt() {
		this.createdAt = new Date();
	}
	public Date getCreatedAt() {
		return this.createdAt;
	}
}