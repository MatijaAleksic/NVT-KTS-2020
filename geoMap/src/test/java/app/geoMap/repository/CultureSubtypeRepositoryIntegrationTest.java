package app.geoMap.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import app.geoMap.model.CultureSubtype;

import static app.geoMap.constants.CultureSubtypeConstants.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CultureSubtypeRepositoryIntegrationTest {
	
	@Autowired
	private CultureSubtypeRepository cultureSubtypeRepository;
	
	@Test
	public void testFindByName() {
		CultureSubtype found = cultureSubtypeRepository.findByName(DB_SUBTYPE);
		assertEquals(DB_SUBTYPE, found.getName());
		
	}
	
	@Test
	public void testFindByNameIdNot() {
		CultureSubtype found = cultureSubtypeRepository.findByNameAndIdNot(DB_SUBTYPE, DB_SUBTYPE_ID);
		assertNull(found);
	}
	
	
	
	

}
