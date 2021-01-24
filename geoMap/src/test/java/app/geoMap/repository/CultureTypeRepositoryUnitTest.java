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

import app.geoMap.model.CultureType;

import static app.geoMap.constants.CultureTypeConstants.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")

public class CultureTypeRepositoryUnitTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private CultureTypeRepository cultureTypeRepository;
	
	@Before
	public void setUp() {
		entityManager.persist(new CultureType(NEW_TYPE));
		 
	}
	
	@Test
	public void testFindByName() {
		CultureType found = cultureTypeRepository.findByName(NEW_TYPE);
		assertEquals(NEW_TYPE, found.getName());
	}
	
	

}
