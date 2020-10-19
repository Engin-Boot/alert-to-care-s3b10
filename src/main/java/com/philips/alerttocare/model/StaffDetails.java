package com.philips.alerttocare.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "StaffDetails")
/**
 * This StaffDetails class is an entity with 
 * id , password, designation and 
 * Previliges of admin to add /remove 
 * patient from the database
 * monitored
 **/
public class StaffDetails implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "Username", unique = true)
	private String username;
	
	@Column(name = "Password")
	private String password;
	
	@Column(name = "Designation")
	private String designation;
	
	@Column(name = "Admin Previliges")
	private boolean adminPrevilige;
	
	@Column(name = "Created At")
	private Date createdAt;
	
	public StaffDetails() {
		super();
	}
	
	@OneToMany(mappedBy="staffdetails")
	private Set<Patient> patient;

	public StaffDetails(String username, String password, String designation, boolean adminPrevilige) {
		super();
		this.username = username;
		this.password = password;
		this.designation = designation;
		this.adminPrevilige = adminPrevilige;
	}

	public Long getStaffId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public boolean isAdminPrevilige() {
		return adminPrevilige;
	}

	public void setAdminPrevilige(boolean adminPrevilige) {
		this.adminPrevilige = adminPrevilige;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	@PrePersist
	public void setCreatedAt() {
		this.createdAt = new Date();
	}
	
}
