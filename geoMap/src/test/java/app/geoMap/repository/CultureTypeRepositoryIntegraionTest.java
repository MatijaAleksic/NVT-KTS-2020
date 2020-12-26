package app.geoMap.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import app.geoMap.model.CultureType;

import static app.geoMap.constants.CultureTypeConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CultureTypeRepositoryIntegraionTest {
	
	@Autowired
	private CultureTypeRepository cultureTypeRepository;	
	
	@Test
	public void testFindByName() {
		CultureType found = cultureTypeRepository.findByName(DB_TYPE);	
		assertEquals(DB_TYPE, found.getName());
	}
	
	@Test
	public void testFindByNameAndIdNot() {
		CultureType found = cultureTypeRepository.findByNameAndIdNot(DB_TYPE, DB_TYPE_ID);
		assertNull(found);
	}
	
	
		
	
	

}
