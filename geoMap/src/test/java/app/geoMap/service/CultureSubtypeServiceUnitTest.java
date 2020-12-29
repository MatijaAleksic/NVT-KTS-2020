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

import app.geoMap.model.CultureSubtype;
import app.geoMap.model.CultureType;
import app.geoMap.repository.CultureSubtypeRepository;

import static app.geoMap.constants.CultureSubtypeConstants.*;
import static app.geoMap.constants.CultureTypeConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CultureSubtypeServiceUnitTest {

	@Autowired
	private CultureSubtypeService cultureSubtypeService;
	
	@MockBean
	private CultureSubtypeRepository cultureSubtypeRepository;
	
	@Before
	public void setUp() {
		List<CultureSubtype> cultureSubtypes = new ArrayList<>();
		cultureSubtypes.add(new CultureSubtype(NEW_SUBTYPE));
		cultureSubtypes.add(new CultureSubtype(NEW_SUBTYPE2));
		
		
		given(cultureSubtypeRepository.findAll()).willReturn(cultureSubtypes);
		
		CultureSubtype cultureSubtype = new CultureSubtype(NEW_SUBTYPE);
		CultureSubtype savedCultureSubtype = new CultureSubtype(NEW_SUBTYPE);
		savedCultureSubtype.setId(SUBTYPE_ID);
		
		given(cultureSubtypeRepository.findById(SUBTYPE_ID)).willReturn(java.util.Optional.of(savedCultureSubtype));
		given(cultureSubtypeRepository.findByName(NEW_SUBTYPE)).willReturn(cultureSubtype);
		
		CultureSubtype cultureSubtypeFound = new CultureSubtype(DB_SUBTYPE);
		given(cultureSubtypeRepository.findByName(DB_SUBTYPE)).willReturn(cultureSubtypeFound);
		
		given(cultureSubtypeRepository.save(cultureSubtype)).willReturn(savedCultureSubtype);
		
		doNothing().when(cultureSubtypeRepository).delete(savedCultureSubtype);
		
		
	}
	/*
	@Test
	public void testFindAll() {
		List<CultureSubtype> found = cultureSubtypeService.findAll();
		
		verify(cultureSubtypeRepository, times(1)).findAll();
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
	}
	
	@Test
	public void testFindById() {
		CultureSubtype found = cultureSubtypeService.findOne(SUBTYPE1_ID);
		
		verify(cultureSubtypeRepository, times(1)).findById(SUBTYPE1_ID);
		assertEquals(SUBTYPE1_ID, found.getId());
	}*/
	
	@Test
	public void testCreate() throws Exception {
		CultureSubtype cultureSubtype = new CultureSubtype(NEW_SUBTYPE);
		//CultureType cultureType = new CultureType(DB_TYPE_ID, DB_TYPE  );
		
		//cultureSubtype.setCultureType(cultureType);
		CultureSubtype created = cultureSubtypeService.create(cultureSubtype);
		
		verify(cultureSubtypeRepository, times(1)).findByName(NEW_SUBTYPE);
		verify(cultureSubtypeRepository, times(1)).save(cultureSubtype);
		
		assertEquals(NEW_SUBTYPE, created.getName());
		
	}
	
	@Test
	public void testGiven_nameAlreadyExists() throws Exception {
		CultureSubtype cultureSubtype = new CultureSubtype(DB_SUBTYPE);
		CultureType cultureType = new CultureType(DB_TYPE);
		
		cultureSubtype.setCultureType(cultureType);
		CultureSubtype created = cultureSubtypeService.create(cultureSubtype);
		
		verify(cultureSubtypeRepository, times(1)).findByName(DB_SUBTYPE);
		
		assertEquals(null, created);
	}
	
	@Test
	public void testUpdate() throws Exception {
		CultureSubtype cultureSubtype = new CultureSubtype(NEW_SUBTYPE);
		CultureType cultureType = new CultureType(DB_TYPE);
		
		cultureSubtype.setCultureType(cultureType);
		CultureSubtype created = cultureSubtypeService.update(cultureSubtype, SUBTYPE_ID);
		
		verify(cultureSubtypeRepository, times(1)).findById(SUBTYPE_ID);
		verify(cultureSubtypeRepository, times(1)).findByNameAndIdNot(NEW_SUBTYPE, SUBTYPE_ID);
		
		assertEquals(NEW_SUBTYPE, created.getName());
		
	}
	
	@Test
	public void testDelete() throws Exception {
	
		cultureSubtypeService.delete(SUBTYPE_ID);
		
		CultureSubtype savedCultureSubtype = new CultureSubtype(NEW_SUBTYPE);
		savedCultureSubtype.setId(SUBTYPE_ID);
		
		//CultureType cultureType = new CultureType(DB_TYPE_ID);
		//cultureSubtype.setCultureType(cultureType);
		
		verify(cultureSubtypeRepository, times(1)).findById(SUBTYPE_ID);
	}
	/*
	@Test
	public void testFindSubtypesOfTypes() {
		List<CultureSubtype> found = cultureSubtypeService.findOne(TYPE_ID, SUBTYPE1_ID);
	}
	
	*/
	
	
	
}
