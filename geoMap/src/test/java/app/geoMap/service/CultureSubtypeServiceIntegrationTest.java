package app.geoMap.service;

import static app.geoMap.constants.CultureSubtypeConstants.*;
import static app.geoMap.constants.CultureTypeConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import app.geoMap.model.CultureSubtype;
import app.geoMap.model.CultureType;
import app.geoMap.repository.CultureSubtypeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CultureSubtypeServiceIntegrationTest {

	@Autowired
	private CultureSubtypeService cultureSubtypeService;
	
	
	
	@Test
	public void testFindAll() {
		List<CultureSubtype> found = cultureSubtypeService.findAll();
		
		assertEquals(FIND_ALL_SUBTYPES, found.size());
	}
	
	@Test
	public void testFindById() {
		CultureSubtype found = cultureSubtypeService.findOne(SUBTYPE_ID);
		
		assertEquals(SUBTYPE_ID, found.getId());
	}
	
	@Test
	public void testCreate() throws Exception {
		CultureSubtype cultureSubtype = new CultureSubtype(NEW_SUBTYPE);
		CultureType cultureType = new CultureType(DB_TYPE_ID, DB_TYPE  );
		
		cultureSubtype.setCultureType(cultureType);
		CultureSubtype created = cultureSubtypeService.create(cultureSubtype);
		
		
		assertEquals(NEW_SUBTYPE, created.getName());
		
	}
	
	@Test
	public void testGiven_nameAlreadyExists() throws Exception {
		CultureSubtype cultureSubtype = new CultureSubtype(DB_SUBTYPE);
		CultureType cultureType = new CultureType(DB_TYPE);
		
		cultureSubtype.setCultureType(cultureType);
		CultureSubtype created = cultureSubtypeService.create(cultureSubtype);
		
		
		assertEquals(null, created);
	}
	
	@Test
	public void testUpdate() throws Exception {
		CultureSubtype cultureSubtype = new CultureSubtype(NEW_SUBTYPE);
		CultureType cultureType = new CultureType(DB_TYPE);
		
		cultureSubtype.setCultureType(cultureType);
		CultureSubtype created = cultureSubtypeService.update(cultureSubtype, SUBTYPE_ID);
		
		assertEquals(NEW_SUBTYPE, created.getName());
		
	}
	
	@Test
	public void testDelete() throws Exception {
		CultureSubtype cultureSubtype = new CultureSubtype(DB_SUBTYPE);
		CultureType cultureType = new CultureType(DB_TYPE_ID);
		cultureSubtype.setCultureType(cultureType);
		
		cultureSubtypeService.delete(DB_SUBTYPE_ID);
		
	}
}
