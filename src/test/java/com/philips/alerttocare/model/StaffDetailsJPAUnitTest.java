package com.philips.alerttocare.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.philips.alerttocare.repository.StaffDetailsRepository;

@RunWith(SpringRunner.class)
@DataJpaTest

/**
 * This StaffDetailsJPAUnitTest Class
 * uses its repository and entity manager
 * and does JPA unit testings like
 * addition, deletion of StaffDetails entity on
 * repository and finding them by Id's
 * and tested a customized method of StaffDetailsRepository
 * where in you can find list of StaffPeople 
 * based on their designation containing given string
 */

public class StaffDetailsJPAUnitTest {

	  @Autowired
	  private TestEntityManager entityManager;

	  @Autowired
	  StaffDetailsRepository repository;

	  @Test
	  public void should_find_no_StaffDetails_if_repository_is_empty() {
	    Iterable<StaffDetails> staffdetails = repository.findAll();
  
	    assertThat(staffdetails).isEmpty();
	  }

	  @Test
	  public void should_store_a_StaffDetails() {
		  StaffDetails staffdetail = repository.save(new StaffDetails("Pranay", "vasu123" , "Doctor" , true));
		  Date dateobj = staffdetail.getCreatedAt();
		  staffdetail.setAdminPrevilige(false);

	    assertThat(staffdetail).hasFieldOrPropertyWithValue("username", "Pranay");
	    assertThat(staffdetail).hasFieldOrPropertyWithValue("password", "vasu123");
	    assertThat(staffdetail).hasFieldOrPropertyWithValue("designation", "Doctor");
	    assertThat(staffdetail).hasFieldOrPropertyWithValue("adminPrevilige", false);
	    assertThat(staffdetail).hasFieldOrPropertyWithValue("createdAt", dateobj);
	  }

	  @Test
	  public void should_find_all_staffdetails() {
		StaffDetails staffdetail1 = new StaffDetails("Pranay", "vasu123" , "Doctor" , true);
	    entityManager.persist(staffdetail1);

	    StaffDetails staffdetail2 = new StaffDetails("Harsha", "ha123" , "Doctor" , true);
	    entityManager.persist(staffdetail2);

	    StaffDetails staffdetail3 = new StaffDetails("Praveen", "pra123" , "Nurse" , false);
	    entityManager.persist(staffdetail3);

	    Iterable<StaffDetails> staffdetails = repository.findAll();

	    assertThat(staffdetails).hasSize(3).contains(staffdetail1, staffdetail2, staffdetail3);
	  }

	  @Test
	  public void should_find_staffdetail_by_id() {
		StaffDetails staffdetail1 = new StaffDetails("Pranay", "vasu123" , "Doctor" , true);
	    entityManager.persist(staffdetail1);

	    StaffDetails staffdetail2 = new StaffDetails("Praveen", "pra123" , "Nurse" , false);
	    entityManager.persist(staffdetail2);

	    StaffDetails foundStaffDetails = repository.findById(staffdetail2.getStaffId()).get();

	    assertThat(foundStaffDetails).isEqualTo(staffdetail2);
	  }
	  
	  @Test
	  public void should_find_Designation_by_Name_containing_string() {
		  StaffDetails staffdetails1 = new StaffDetails("Harsha", "ha123" , "Doctor" , true);
	    entityManager.persist(staffdetails1);

	    StaffDetails staffdetails2 = new StaffDetails("Pranay", "vasu123" , "Doctor" , true);
	    entityManager.persist(staffdetails2);

	    StaffDetails staffdetails3 = new StaffDetails("Praveen", "pra123" , "Nurse" , false);
	    entityManager.persist(staffdetails3);

	    Iterable<StaffDetails> staffdetailsorials = repository.findByDesignationContaining("Nurse");

	    assertThat(staffdetailsorials).hasSize(1).contains(staffdetails3);
	  }
	
	  @Test
	  public void should_update_staffdetail_by_id() {
		  StaffDetails staffdetail1 = new StaffDetails("Praveen", "pra123" , "Nurse" , false);
	    entityManager.persist(staffdetail1);

	    StaffDetails staffdetail2 = new StaffDetails("Pranay", "vasu123" , "Doctor" , true);
	    entityManager.persist(staffdetail2);

	    StaffDetails updatedStaffDetails = new StaffDetails("PranayBunari", "va@123" , "Doctor" , true);

	    StaffDetails staffdetail = repository.findById(staffdetail2.getStaffId()).get();
	    staffdetail.setUsername(updatedStaffDetails.getUsername());
	    staffdetail.setPassword(updatedStaffDetails.getPassword());
	    staffdetail.setDesignation(updatedStaffDetails.getDesignation());
	   
	    repository.save(staffdetail);

	    StaffDetails checkStaffDetails = repository.findById(staffdetail2.getStaffId()).get();
	    
	    assertThat(checkStaffDetails.getStaffId()).isEqualTo(staffdetail2.getStaffId());
	    assertThat(checkStaffDetails.getUsername()).isEqualTo(updatedStaffDetails.getUsername());
	    assertThat(checkStaffDetails.getPassword()).isEqualTo(updatedStaffDetails.getPassword());
	    assertThat(checkStaffDetails.getDesignation()).isEqualTo(updatedStaffDetails.getDesignation());
	   
	  }

	  @Test
	  public void should_delete_staffdetail_by_id() {
		  StaffDetails staffdetail1 = new StaffDetails("Pranay", "vasu123" , "Doctor" , true);
	    entityManager.persist(staffdetail1);

	    StaffDetails staffdetail2 = new StaffDetails("Praveen", "pra123" , "Nurse" , false);
	    entityManager.persist(staffdetail2);

	    StaffDetails staffdetail3 = new StaffDetails("Harsha", "ha123" , "Doctor" , true);
	    entityManager.persist(staffdetail3);

	    repository.deleteById(staffdetail2.getStaffId());

	    Iterable<StaffDetails> staffdetails = repository.findAll();

	    assertThat(staffdetails).hasSize(2).contains(staffdetail1, staffdetail3);
	  }

	  @Test
	  public void should_delete_all_departments() {
	    entityManager.persist(new StaffDetails("Pranay", "vasu123" , "Doctor" , true));
	    entityManager.persist(new StaffDetails("Praveen", "pra123" , "Nurse" , false));

	    repository.deleteAll();

	    assertThat(repository.findAll()).isEmpty();
	  }
}
