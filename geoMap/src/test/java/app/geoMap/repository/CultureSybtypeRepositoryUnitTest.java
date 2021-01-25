package app.geoMap.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import app.geoMap.model.CultureSubtype;
import static app.geoMap.constants.CultureSubtypeConstants.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")

public class CultureSybtypeRepositoryUnitTest {

	 @Autowired
	 private TestEntityManager entityManager;
	 
	 @Autowired
	 private CultureSubtypeRepository cultureSubtypeRepository;
	 
	 @Autowired
	 private CultureTypeRepository cultureTypeRepository;
	 
	 @Before
	 public void setUp() {
		 entityManager.persist(new CultureSubtype(NEW_SUBTYPE));
	 }
	 
	 
	 @Test
	 public void testFindByName() {
		 CultureSubtype found = cultureSubtypeRepository.findByName(DB_SUBTYPE);
		 assertEquals(DB_SUBTYPE, found.getName());
	 }
	 
	 
	 
	 
}
