package com.philips.alerttocare.model;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.philips.alerttocare.repository.HealthMonitorRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
/**
 * This HealthMonitorJPAUnitTest Class
 * uses its repository and entity manager
 * and does JPA unit testings like
 * addition, deletion of HealthMonitor entities on
 * repository and finding them by Id's
 */

public class HealthMonitorJPAUnitTest {

	  @Autowired
	  private TestEntityManager entityManager;

	  @Autowired
	  HealthMonitorRepository repository;

	  @Test
	  public void should_find_no_HealthMonitor_if_repository_is_empty() {
	    Iterable<HealthMonitor> healthmonitors = repository.findAll();
  
	    assertThat(healthmonitors).isEmpty();
	  }

	  @Test
	  public void should_store_a_HealthMonitor() {
		  HealthMonitor healthmonitor = repository.save(new HealthMonitor("HealthMonitor1"));
		  Date dateobj  = healthmonitor.getCreatedAt();

	    assertThat(healthmonitor).hasFieldOrPropertyWithValue("label", "HealthMonitor1");
	    assertThat(healthmonitor).hasFieldOrPropertyWithValue("createdAt", dateobj);
	    
	  }

	  @Test
	  public void should_find_all_HealthMonitors() {
		HealthMonitor healthmonitors1 = new HealthMonitor("HealthMonitor1");
	    entityManager.persist(healthmonitors1);

	    HealthMonitor healthmonitors2 = new HealthMonitor("HealthMonitor2");
	    entityManager.persist(healthmonitors2);

	     HealthMonitor healthmonitors3= new HealthMonitor("HealthMonitor3");
	    entityManager.persist(healthmonitors3);

	    Iterable<HealthMonitor> helmonitors = repository.findAll();

	    assertThat(helmonitors).hasSize(3).contains(healthmonitors1, healthmonitors2, healthmonitors3);
	  }

	  @Test
	  public void should_find_HealthMonitor_by_id() {
		HealthMonitor healthmonitor1 = new HealthMonitor("HealthMonitor1");
	    entityManager.persist(healthmonitor1);

	    HealthMonitor healthmonitor2 = new HealthMonitor("HealthMonitor2");
	    entityManager.persist(healthmonitor2);

	    HealthMonitor foundHealthMonitor = repository.findById(healthmonitor2.getId()).get();

	    assertThat(foundHealthMonitor).isEqualTo(healthmonitor2);
	  }

	 
	  @Test
	  public void should_update_HealthMonitor_by_id() {
		  HealthMonitor healthmonitor1 = new HealthMonitor("HealthMonitor1");
	    entityManager.persist(healthmonitor1);

	    HealthMonitor healthmonitor2 = new HealthMonitor("HealthMonitor2");
	    entityManager.persist(healthmonitor2);

	    HealthMonitor updatedHealthMonitor = new HealthMonitor("Updated HealthMonitor label");

	    HealthMonitor helmonitor = repository.findById(healthmonitor2.getId()).get();
	    helmonitor.setLabel(updatedHealthMonitor.getLabel());
	   
	    repository.save(helmonitor);

	    HealthMonitor checkHealthMonitor = repository.findById(healthmonitor2.getId()).get();
	    
	    assertThat(checkHealthMonitor.getId()).isEqualTo(healthmonitor2.getId());
	    assertThat(checkHealthMonitor.getLabel()).isEqualTo(updatedHealthMonitor.getLabel());
	   
	  }

	  @Test
	  public void should_delete_HealthMonitor_by_id() {
		  HealthMonitor healthmonitor1 = new HealthMonitor("HealthMonitor1");
	    entityManager.persist(healthmonitor1);

	    HealthMonitor healthmonitor2 = new HealthMonitor("HealthMonitor2");
	    entityManager.persist(healthmonitor2);

	    HealthMonitor healthmonitor3 = new HealthMonitor("HealthMonitor3");
	    entityManager.persist(healthmonitor3);

	    repository.deleteById(healthmonitor2.getId());

	    Iterable<HealthMonitor> HealthMonitors = repository.findAll();

	    assertThat(HealthMonitors).hasSize(2).contains(healthmonitor1, healthmonitor3);
	  }

	  @Test
	  public void should_delete_all_departments() {
	    entityManager.persist(new HealthMonitor("HealthMonitor1"));
	    entityManager.persist(new HealthMonitor("HealthMonitor2"));

	    repository.deleteAll();

	    assertThat(repository.findAll()).isEmpty();
	  }
}


