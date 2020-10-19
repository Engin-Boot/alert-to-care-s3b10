package com.philips.alerttocare.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.philips.alerttocare.repository.HealthStatusRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
/**
 * This HealthStatusJPAUnitTest Class
 * uses its repository and entity manager
 * and does JPA unit testings like
 * addition, deletion of HealthStatus entities on
 * repository and finding them by Id's
 */

public class HealthStatusJPAUnitTest {

	  @Autowired
	  private TestEntityManager entityManager;

	  @Autowired
	  HealthStatusRepository repository;

	  @Test
	  public void should_find_no_HealthStatus_if_repository_is_empty() {
	    Iterable<HealthStatus> healthstatus = repository.findAll();
  
	    assertThat(healthstatus).isEmpty();
	  }

	  @Test
	  public void should_store_a_HealthStatus() {
		 
		  HealthStatus healthstatus = repository.save(new HealthStatus(88.9 , 22.5 , 90.23 , 77.87 , null , null));
		  Date dateobj = healthstatus.getCreatedAt();

	    assertThat(healthstatus).hasFieldOrPropertyWithValue("heartrate", 88.9);
	    assertThat(healthstatus).hasFieldOrPropertyWithValue("bp", 22.5);
	    assertThat(healthstatus).hasFieldOrPropertyWithValue("spo2", 90.23);
	    assertThat(healthstatus).hasFieldOrPropertyWithValue("respiratoryrate", 77.87);
	    assertThat(healthstatus).hasFieldOrPropertyWithValue("createdAt", dateobj);
	  }

	  @Test
	  public void should_find_all_healthstatuss() {
			  
		HealthStatus healthstatus1 = new HealthStatus(88.9 , 22.5 , 90.23 , 77.87 , null , null);
	    entityManager.persist(healthstatus1);

	    HealthStatus healthstatus2 = new HealthStatus(88.9 , 22.5 , 90.23 , 77.87 , null , null);
	    entityManager.persist(healthstatus2);

	    HealthStatus healthstatus3 = new HealthStatus(88.9 , 22.5 , 90.23 , 77.87 , null , null);
	    entityManager.persist(healthstatus3);

	    Iterable<HealthStatus> healthstatuss = repository.findAll();

	    assertThat(healthstatuss).hasSize(3).contains(healthstatus1, healthstatus2, healthstatus3);
	  }

	  @Test
	  public void should_find_healthstatus_by_id() {
		  
		  
		HealthStatus healthstatus1 = new HealthStatus(88.9 , 22.5 , 90.23 , 77.87 , null , null);
	    entityManager.persist(healthstatus1);

	    HealthStatus healthstatus2 = new HealthStatus(88.9 , 22.5 , 90.23 , 77.87 , null , null);
	    entityManager.persist(healthstatus2);

	    HealthStatus foundHealthStatus = repository.findById(healthstatus2.getId()).get();

	    assertThat(foundHealthStatus).isEqualTo(healthstatus2);
	  }

	  @Test
	  public void should_update_healthstatus_by_id() {
		  Occupancy occupancy1 =  new Occupancy();
		  HealthMonitor healthmonitor1 = new HealthMonitor();
		  
		  Occupancy occupancy2 =  new Occupancy();
		  HealthMonitor healthmonitor2 = new HealthMonitor();
		  
		  Occupancy occupancy3 =  new Occupancy();
		  HealthMonitor healthmonitor3 = new HealthMonitor();
		  
		  HealthStatus healthstatus1 = new HealthStatus(88.9 , 22.5 , 90.23 , 77.87 , occupancy1 , healthmonitor1);
	    entityManager.persist(healthstatus1);

	    HealthStatus healthstatus2 = new HealthStatus(88.9 , 22.5 , 90.23 , 77.87 , occupancy2 , healthmonitor2);
	    entityManager.persist(healthstatus2);

	    HealthStatus updatedHealthStatus = new HealthStatus(85.9 , 58.5 , 70.23 , 83.87 , occupancy3 , healthmonitor3);

	    HealthStatus healthstatus = repository.findById(healthstatus2.getId()).get();
	    healthstatus.setHeartrate(updatedHealthStatus.getHeartrate());
	    healthstatus.setBp(updatedHealthStatus.getBp());
	    healthstatus.setRespiratoryrate(updatedHealthStatus.getRespiratoryrate());
	    healthstatus.setSpo2(updatedHealthStatus.getSpo2());
	    
	    healthstatus.setMonitor(updatedHealthStatus.getMonitor());
	    healthstatus.setOccupancy(updatedHealthStatus.getOccupancy());
	    
	   
	    repository.save(healthstatus);

	    HealthStatus checkHealthStatus = repository.findById(healthstatus2.getId()).get();
	    
	    assertThat(checkHealthStatus.getId()).isEqualTo(healthstatus2.getId());
	    assertThat(checkHealthStatus.getHeartrate()).isEqualTo(updatedHealthStatus.getHeartrate());
	    assertThat(checkHealthStatus.getBp()).isEqualTo(updatedHealthStatus.getBp());
	    assertThat(checkHealthStatus.getRespiratoryrate()).isEqualTo(updatedHealthStatus.getRespiratoryrate());
	    assertThat(checkHealthStatus.getSpo2()).isEqualTo(updatedHealthStatus.getSpo2());
	    assertThat(checkHealthStatus.getMonitor()).isEqualTo(updatedHealthStatus.getMonitor());
	    assertThat(checkHealthStatus.getOccupancy()).isEqualTo(updatedHealthStatus.getOccupancy());
	   
	  }

	  @Test
	  public void should_delete_healthstatus_by_id() {
		  
		  
		  HealthStatus healthstatus1 = new HealthStatus(88.9 , 22.5 , 90.23 , 77.87 , null , null);
	    entityManager.persist(healthstatus1);

	    HealthStatus healthstatus2 = new HealthStatus(88.9 , 22.5 , 90.23 , 77.87 , null , null);
	    entityManager.persist(healthstatus2);

	    HealthStatus healthstatus3 = new HealthStatus(85.9 , 58.5 , 70.23 , 83.87 , null , null);
	    entityManager.persist(healthstatus3);

	    repository.deleteById(healthstatus2.getId());

	    Iterable<HealthStatus> healthstatuss = repository.findAll();

	    assertThat(healthstatuss).hasSize(2).contains(healthstatus1, healthstatus3);
	  }

	  @Test
	  public void should_delete_all_departments() {
		  		  
	    entityManager.persist(new HealthStatus(88.9 , 22.5 , 90.23 , 77.87 , null , null));
	    entityManager.persist(new HealthStatus(88.9 , 22.5 , 90.23 , 77.87 , null , null));

	    repository.deleteAll();

	    assertThat(repository.findAll()).isEmpty();
	  }
}
