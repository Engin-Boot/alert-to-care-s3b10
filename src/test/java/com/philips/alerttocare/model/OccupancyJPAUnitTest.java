package com.philips.alerttocare.model;


import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertEquals;
//import java.util.List;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.philips.alerttocare.repository.OccupancyRepository;

@RunWith(SpringRunner.class)
@DataJpaTest

/**
 * This OccupancyJPAUnitTest Class
 * uses its repository and entity manager
 * and does JPA unit testings like
 * addition, deletion of Occupancy entities on
 * repository and finding them by Id's
 */

public class OccupancyJPAUnitTest {

	  @Autowired
	  private TestEntityManager entityManager;

	  @Autowired
	  OccupancyRepository repository;

	  @Test
	  public void should_find_no_Occupancy_if_repository_is_empty() {
	    Iterable<Occupancy> occupancies = repository.findAll();
  
	    assertThat(occupancies).isEmpty();
	  }

	  @Test
	  public void should_store_a_Occupancy() {
		  Date disdate = new Date();
		  Icu icu = new Icu();
		  Bed bed = new Bed();
		  Patient patient = new Patient();
		  
		  
		  Occupancy occupancy = repository.save(new Occupancy(disdate , bed , patient, icu));
		  Date dateobj = occupancy.getOccupiedAt();
	  
	    assertThat(occupancy).hasFieldOrPropertyWithValue("dischargedAt", disdate);
	    assertThat(occupancy).hasFieldOrPropertyWithValue("bed", bed);
	    assertThat(occupancy).hasFieldOrPropertyWithValue("patient", patient);
	    assertThat(occupancy).hasFieldOrPropertyWithValue("icu", icu);
	    assertThat(occupancy).hasFieldOrPropertyWithValue("occupiedAt", dateobj);
	  }

	  @Test
	  public void should_find_all_Occupancies() {
		  Date disdate1 = new Date();
		
		  Date disdate2 = new Date();
		  
		  Date disdate3 = new Date();
		  
		  
		Occupancy occupancy1 = new Occupancy( disdate1 , null , null, null);
	    entityManager.persist(occupancy1);

	    Occupancy occupancy2 = new Occupancy( disdate2 , null , null, null);
	    entityManager.persist(occupancy2);

	    Occupancy occupancy3 = new Occupancy( disdate3 , null , null, null);
	    entityManager.persist(occupancy3);

	    Iterable<Occupancy> occupancys = repository.findAll();

	    assertThat(occupancys).hasSize(3).contains(occupancy1, occupancy2, occupancy3);
	  }

	  @Test
	  public void should_find_occupancy_by_id() {
		  
		  Date disdate1 = new Date();
		  
		  Date disdate2 = new Date();
		  
		  
		Occupancy occupancy1 = new Occupancy( disdate1 , null , null, null);
	    entityManager.persist(occupancy1);

	    Occupancy occupancy2 = new Occupancy( disdate2 , null , null, null);
	    entityManager.persist(occupancy2);

	    Occupancy foundOccupancy = repository.findById(occupancy2.getId()).get();

	    assertThat(foundOccupancy).isEqualTo(occupancy2);
	  }


	  @Test
	  public void should_update_occupancy_by_id() {
		 
		  Date disdate1 = new Date();
		  Icu icu1 = new Icu();
		  Bed bed1 = new Bed();
		  Patient patient1 = new Patient();
		  
		  
		  Date disdate2 = new Date();
		  Icu icu2 = new Icu();
		  Bed bed2 = new Bed();
		  Patient patient2 = new Patient();
		  
		  
		  Date disdate3 = new Date();
		  Icu icu3 = new Icu();
		  Bed bed3 = new Bed();
		  Patient patient3 = new Patient();
		  
		  Occupancy occupancy1 = new Occupancy( disdate1 , bed1 , patient1, icu1);
	    entityManager.persist(occupancy1);

	    Occupancy occupancy2 =new Occupancy( disdate2 , bed2 , patient2, icu2);
	    entityManager.persist(occupancy2);

	    Occupancy updatedOccupancy = new Occupancy(disdate3 , bed3 , patient3, icu3);

	    Occupancy occupancy = repository.findById(occupancy2.getId()).get();
	    occupancy.setPatient(updatedOccupancy.getPatient());
	    occupancy.setDischargedAt(updatedOccupancy.getDischargedAt());
	    occupancy.setBed(updatedOccupancy.getBed());
	    occupancy.setIcu(updatedOccupancy.getIcu());
	   
	    repository.save(occupancy);

	    Occupancy checkOccupancy = repository.findById(occupancy2.getId()).get();
	    
	    assertThat(checkOccupancy.getId()).isEqualTo(occupancy2.getId());
	    assertThat(checkOccupancy.getPatient()).isEqualTo(updatedOccupancy.getPatient());
	    assertThat(checkOccupancy.getDischargedAt()).isEqualTo(updatedOccupancy.getDischargedAt());
	    assertThat(checkOccupancy.getBed()).isEqualTo(updatedOccupancy.getBed());
	    assertThat(checkOccupancy.getIcu()).isEqualTo(updatedOccupancy.getIcu());
	   
	  }

	  @Test
	  public void should_delete_occupancy_by_id() {
		  
		  Date disdate1 = new Date();
		  
		  Date disdate2 = new Date();
		  
		  Date disdate3 = new Date();
		  
		  
		  Occupancy occupancy1 = new Occupancy( disdate1 , null , null, null);
	    entityManager.persist(occupancy1);

	    Occupancy occupancy2 = new Occupancy( disdate2 , null , null, null);
	    entityManager.persist(occupancy2);

	    Occupancy occupancy3 = new Occupancy(disdate3 , null , null, null);
	    entityManager.persist(occupancy3);

	    repository.deleteById(occupancy2.getId());

	    Iterable<Occupancy> occupancys = repository.findAll();

	    assertThat(occupancys).hasSize(2).contains(occupancy1, occupancy3);
	  }

	  @Test
	  public void should_delete_all_departments() {
		  
		  Date disdate2 = new Date();
		  
		  Date disdate3 = new Date();
		  
	    entityManager.persist(new Occupancy( disdate2 , null , null, null));
	    entityManager.persist(new Occupancy( disdate3 , null , null, null));

	    repository.deleteAll();

	    assertThat(repository.findAll()).isEmpty();
	  }

}
