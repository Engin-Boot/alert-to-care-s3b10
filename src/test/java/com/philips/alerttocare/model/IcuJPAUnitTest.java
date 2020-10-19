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

import com.philips.alerttocare.repository.IcuRepository;

@RunWith(SpringRunner.class)
@DataJpaTest

/**
 * This IcuJPAUnitTest Class
 * uses its repository and entity manager
 * and does JPA unit testings like
 * addition, deletion of Icu entities on
 * repository and finding them by Id's
 */

public class IcuJPAUnitTest {

	  @Autowired
	  private TestEntityManager entityManager;

	  @Autowired
	  IcuRepository repository;

	  @Test
	  public void should_find_no_Icu_if_repository_is_empty() {
	    Iterable<Icu> icus = repository.findAll();
    
	    assertThat(icus).isEmpty();
	  }

	  @Test
	  public void should_store_a_Icu() {
		  Icu icu = repository.save(new Icu("ICU1"));
		  Date dateobj = icu.getCreatedAt();

	    assertThat(icu).hasFieldOrPropertyWithValue("label", "ICU1");
	    assertThat(icu).hasFieldOrPropertyWithValue("createdAt", dateobj);
	  }

	  @Test
	  public void should_find_all_icus() {
		Icu icu1 = new Icu("ICU1");
	    entityManager.persist(icu1);

	    Icu icu2 = new Icu("ICU2");
	    entityManager.persist(icu2);

	    Icu icu3 = new Icu("ICU3");
	    entityManager.persist(icu3);

	    Iterable<Icu> icus = repository.findAll();

	    assertThat(icus).hasSize(3).contains(icu1, icu2, icu3);
	  }

	  @Test
	  public void should_find_icu_by_id() {
		Icu icu1 = new Icu("ICU1");
	    entityManager.persist(icu1);

	    Icu icu2 = new Icu("ICU2");
	    entityManager.persist(icu2);

	    Icu foundIcu = repository.findById(icu2.getId()).get();

	    assertThat(foundIcu).isEqualTo(icu2);
	  }

	  @Test
	  public void should_update_icu_by_id() {
		  Icu icu1 = new Icu("ICU1");
	    entityManager.persist(icu1);

	    Icu icu2 = new Icu("ICU2");
	    entityManager.persist(icu2);

	    Icu updatedIcu = new Icu("Updated ICU label");

	    Icu icu = repository.findById(icu2.getId()).get();
	    icu.setLabel(updatedIcu.getLabel());
	   
	    repository.save(icu);

	    Icu checkIcu = repository.findById(icu2.getId()).get();
	    
	    assertThat(checkIcu.getId()).isEqualTo(icu2.getId());
	    assertThat(checkIcu.getLabel()).isEqualTo(updatedIcu.getLabel());
	   
	  }

	  @Test
	  public void should_delete_icu_by_id() {
		  Icu icu1 = new Icu("ICU1");
	    entityManager.persist(icu1);

	    Icu icu2 = new Icu("ICu2");
	    entityManager.persist(icu2);

	    Icu icu3 = new Icu("ICU3");
	    entityManager.persist(icu3);

	    repository.deleteById(icu2.getId());

	    Iterable<Icu> icus = repository.findAll();

	    assertThat(icus).hasSize(2).contains(icu1, icu3);
	  }

	  @Test
	  public void should_delete_all_departments() {
	    entityManager.persist(new Icu("ICU1"));
	    entityManager.persist(new Icu("ICU2"));

	    repository.deleteAll();

	    assertThat(repository.findAll()).isEmpty();
	  }
}
