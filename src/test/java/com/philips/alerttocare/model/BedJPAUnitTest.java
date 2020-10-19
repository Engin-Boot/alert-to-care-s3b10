package com.philips.alerttocare.model;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.philips.alerttocare.repository.BedRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
/**
 * This BedJPAunitTest Class
 * uses its repository and entity manager
 * and does JPA unit testings like
 * addition, deletion of Bed entities on
 * repository and finding them by Id's
 */
public class BedJPAUnitTest {

	  @Autowired
	  private TestEntityManager entityManager;

	  @Autowired
	  BedRepository repository;

	  @Test
	  public void should_find_no_Bed_if_repository_is_empty() {
	    Iterable<Bed> beds = repository.findAll();
  
	    assertThat(beds).isEmpty();
	  }

	  @Test
	  public void should_store_a_Bed() {
		  Icu icuobj = new Icu();
		  Bed bed = repository.save(new Bed("Bed1" , true , false , icuobj));
		  Date dateobj = bed.getCreatedAt();
		  bed.setAlertstatus(true);

	    assertThat(bed).hasFieldOrPropertyWithValue("label", "Bed1");
	    assertThat(bed).hasFieldOrPropertyWithValue("occupiedFlag", true);
	    assertThat(bed).hasFieldOrPropertyWithValue("alertstatus", true);
	    assertThat(bed).hasFieldOrPropertyWithValue("createdAt", dateobj);
	    assertThat(bed).hasFieldOrPropertyWithValue("icu", icuobj);
	  }

	  @Test
	  public void should_find_all_beds() {

		Bed bed1 = new Bed("Bed1" , true , false , null);
	    entityManager.persist(bed1);

	    Bed bed2 = new Bed("Bed2" , false , false , null);
	    entityManager.persist(bed2);

	    Bed bed3 = new Bed("Bed3" , true , false , null);
	    entityManager.persist(bed3);

	       
	   Iterable<Bed> beds = repository.findAll();

	   assertThat(beds).hasSize(3).contains(bed1, bed2, bed3);
	  }

	  @Test
	  public void should_find_bed_by_id() {
		  Icu icuobj1 = new Icu();
		  Icu icuobj2 = new Icu();
		Bed bed1 = new Bed("Bed1" , true , false , icuobj1);
	    entityManager.persist(bed1);

	    Bed bed2 = new Bed("Bed2" , true , false , icuobj2);
	    entityManager.persist(bed2);

	    Bed foundBed = repository.findById(bed2.getId()).get();

	    assertThat(foundBed).isEqualTo(bed2);
	  }

	  @Test
	  public void should_update_bed_by_id() {
		  Icu icuobj1 = new Icu();
		  Icu icuobj2 = new Icu();
		  Icu icuobj3 = new Icu();
		  
		  Bed bed1 = new Bed("Bed1" , true , false , icuobj1);
	    entityManager.persist(bed1);

	    Bed bed2 = new Bed("Bed2" , true , false , icuobj2);
	    entityManager.persist(bed2);

	    Bed updatedBed = new Bed("UpdatedBedLabel" , false , false , icuobj3);

	    Bed bed = repository.findById(bed2.getId()).get();
	    bed.setLabel(updatedBed.getLabel());
	    bed.setOccupiedFlag(updatedBed.getOccupiedFlag());
	    bed.setIcu(updatedBed.getIcu());
	    
	   
	    repository.save(bed);

	    Bed checkBed = repository.findById(bed2.getId()).get();
	    
	    assertThat(checkBed.getId()).isEqualTo(bed2.getId());
	    assertThat(checkBed.getLabel()).isEqualTo(updatedBed.getLabel());
	    assertThat(checkBed.getOccupiedFlag()).isEqualTo(updatedBed.getOccupiedFlag());
	    assertThat(checkBed.getIcu()).isEqualTo(updatedBed.getIcu());
	   
	  }

	  @Test
	  public void should_delete_bed_by_id() {
		  Bed bed1 = new Bed("Bed1" , true , false , null);
	    entityManager.persist(bed1);

	    Bed bed2 = new Bed("Bed2" , true , false , null);
	    entityManager.persist(bed2);

	    Bed bed3 = new Bed("Bed3" , true , false , null);
	    entityManager.persist(bed3);

	    repository.deleteById(bed2.getId());

	    Iterable<Bed> beds = repository.findAll();

	    assertThat(beds).hasSize(2).contains(bed1, bed3);
	  }

	  @Test
	  public void should_delete_all_departments() {
	    entityManager.persist(new Bed("Bed1" , true , false , null));
	    entityManager.persist(new Bed("Bed2" , true , false , null));

	    repository.deleteAll();

	    assertThat(repository.findAll()).isEmpty();
	  }
}