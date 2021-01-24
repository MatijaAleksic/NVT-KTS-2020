package app.geoMap.service;

import static app.geoMap.constants.CultureTypeConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import app.geoMap.model.CultureType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CultureTypeServiceIntegrationTest {
	
	
	@Autowired
	private CultureTypeService cultureTypeService;
	
		
	
	
	@Test
	public void testFindAll() {
		
		List<CultureType> found = cultureTypeService.findAll();
		
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS_DB, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<CultureType> found = cultureTypeService.findAll(pageable);

		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
		
	}
	
	@Test
	public void testFindById() {
		CultureType found = cultureTypeService.findOne(DB_TYPE_ID);
		
		assertEquals(DB_TYPE_ID, found.getId());
		
	}
	
	@Test
	public void testCreate( ) throws Exception {
		CultureType cultureType = new CultureType(NEW_TYPE);
		CultureType created = cultureTypeService.create(cultureType);
		
		assertEquals(NEW_TYPE, created.getName());
		
	}
	/*
	@Test
	public void testCreate_GivenNameAlreadyExists() throws Exception {
		CultureType cultureType = new CultureType(DB_TYPE);
		CultureType created = cultureTypeService.create(cultureType);
	
		assertEquals(null, created);
		
		
	}*/
	
	@Test
	@Transactional
	public void testUpdate() throws Exception {
		CultureType cultureType = new CultureType(NEW_TYPE);
		CultureType created = cultureTypeService.update(cultureType, DB_TYPE_ID);
		
		
		assertEquals(NEW_TYPE, created.getName());
		
	}
	
	@Test
	@Transactional
	public void testDelete() throws Exception {
		cultureTypeService.delete(DB_TYPE_ID);
		
		CultureType savedCultureType = new CultureType(NEW_TYPE);
		savedCultureType.setId(TYPE_ID);
		
		
		
	}

}
