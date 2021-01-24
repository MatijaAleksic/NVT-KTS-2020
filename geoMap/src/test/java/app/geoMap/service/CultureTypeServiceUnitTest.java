package app.geoMap.service;

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


import app.geoMap.model.CultureType;
import app.geoMap.repository.CultureTypeRepository;

import static app.geoMap.constants.CultureTypeConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CultureTypeServiceUnitTest {
	
	@Autowired
	private CultureTypeService cultureTypeService;
	
	@MockBean
	private CultureTypeRepository cultureTypeRepository;
	
	@Before
	public void setUp() {
		List<CultureType> cultureTypes = new ArrayList<>();
		cultureTypes.add(new CultureType(NEW_TYPE));
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<CultureType> cultureTypePage = new PageImpl<>(cultureTypes,pageable,PAGEABLE_TOTAL_ELEMENTS);
		
		given(cultureTypeRepository.findAll()).willReturn(cultureTypes);
		given(cultureTypeRepository.findAll(pageable)).willReturn(cultureTypePage);
		
		CultureType cultureType = new CultureType(NEW_TYPE);
		CultureType savedCultureType = new CultureType(NEW_TYPE);
		savedCultureType.setId(TYPE_ID);
		
		given(cultureTypeRepository.findById(TYPE_ID)).willReturn(java.util.Optional.of(savedCultureType));
		given(cultureTypeRepository.findByName(NEW_TYPE)).willReturn(null);
		
		CultureType cultureTypeFound = new CultureType(DB_TYPE_ID,DB_TYPE);
		given(cultureTypeRepository.findByName(DB_TYPE)).willReturn(cultureTypeFound);
		
		given(cultureTypeRepository.findByNameAndIdNot(NEW_TYPE, TYPE_ID)).willReturn(null);
		given(cultureTypeRepository.save(org.mockito.ArgumentMatchers.any())).willReturn(cultureType);
		
		doNothing().when(cultureTypeRepository).delete(savedCultureType);
		
	}
	
	@Test
	public void testFindAll() {
		
		List<CultureType> found = cultureTypeService.findAll();
		
		verify(cultureTypeRepository, times(1)).findAll();
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<CultureType> found = cultureTypeService.findAll(pageable);
		
		verify(cultureTypeRepository, times(1)).findAll(pageable);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
		
	}
	
	@Test
	public void testFindById() {
		CultureType found = cultureTypeService.findOne(TYPE_ID);
		
		verify(cultureTypeRepository, times(1)).findById(TYPE_ID);
		assertEquals(TYPE_ID, found.getId());
		
	}
	
	@Test
	public void testCreate( ) throws Exception {
		CultureType cultureType = new CultureType(NEW_TYPE);
		//cultureType.setId(TYPE_ID);
		CultureType created = cultureTypeService.create(cultureType);
		
		
		verify(cultureTypeRepository, times(1)).findByName(NEW_TYPE);
		//verify(cultureTypeRepository, times(1)).save(cultureType);
		
		assertEquals(NEW_TYPE, created.getName());
		
	}
	/*
	@Test
	public void testCreate_GivenNameAlreadyExists() throws Exception {
		CultureType cultureType = new CultureType(DB_TYPE);
		CultureType created = cultureTypeService.create(cultureType);
		
		verify(cultureTypeRepository, times(1)).findByName(DB_TYPE);
		
		assertEquals(null, created);
		
		
	}*/
	
	@Test
	public void testUpdate() throws Exception {
		CultureType cultureType = new CultureType(NEW_TYPE);
		CultureType created = cultureTypeService.update(cultureType, TYPE_ID);
		
		
		verify(cultureTypeRepository, times(1)).findById(TYPE_ID);
		//verify(cultureTypeRepository, times(1)).findByNameAndIdNot(NEW_TYPE, TYPE_ID);
		//verify(cultureTypeRepository, times(1)).save(cultureType);
		
		assertEquals(NEW_TYPE, created.getName());
		
	}
	
	@Test
	public void testDelete() throws Exception {
		cultureTypeService.delete(TYPE_ID);
		
		CultureType savedCultureType = new CultureType(NEW_TYPE);
		savedCultureType.setId(TYPE_ID);
		
		verify(cultureTypeRepository, times(1)).findById(TYPE_ID);
		
		
	}

}
