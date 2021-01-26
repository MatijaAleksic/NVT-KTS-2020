package app.geoMap.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

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

import app.geoMap.model.CulturalOffer;
import app.geoMap.repository.CulturalOfferRepository;

import static app.geoMap.constants.CulturalOfferConstants.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferServiceUnitTest {
	
	@Autowired
	private CulturalOfferService culturalOfferService;
	
	
	@MockBean
	private CulturalOfferRepository culturalOfferRepository;
	
	
	@Before
	public void setUp() {
		List<CulturalOffer> culturalOffers = new ArrayList<>();
		CulturalOffer newCO = new CulturalOffer(NEW_CO_NAME, NEW_CO_LON, NEW_CO_LAT);
		CulturalOffer dbCO = new CulturalOffer(DB_CO_NAME, DB_CO_LON, DB_CO_LAT);
		//culturalOffers.add(newCO);
		dbCO.setId(1L);
		culturalOffers.add(dbCO);
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<CulturalOffer> pageCultureOffer = new PageImpl<>(culturalOffers, pageable, PAGEABLE_TOTAL_ELEMENTS);
		
		given(culturalOfferRepository.findAll()).willReturn(culturalOffers);
		given(culturalOfferRepository.findAll(pageable)).willReturn(pageCultureOffer);
		
		given(culturalOfferRepository.findById(DB_CO_ID)).willReturn(java.util.Optional.of(dbCO));
		given(culturalOfferRepository.findByName(NEW_CO_NAME)).willReturn(null);
		
		given(culturalOfferRepository.save(org.mockito.ArgumentMatchers.any())).willReturn(newCO);
		doNothing().when(culturalOfferRepository).delete(newCO);
		
	}
	
	@Test
	public void testFindAll() {
		List<CulturalOffer> foundCulturalOffer = culturalOfferService.findAll();
		
		verify(culturalOfferRepository, times(1)).findAll();
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, foundCulturalOffer.size());
		
	}
	
	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<CulturalOffer> foundCultureOffer = culturalOfferService.findAll(pageable);
		
		verify(culturalOfferRepository, times(1)).findAll(pageable);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, foundCultureOffer.getNumberOfElements());
	}
	
	@Test
	public void testFindOne() {
		CulturalOffer found = culturalOfferService.findOne(DB_CO_ID);
		
		verify(culturalOfferRepository, times(1)).findById(DB_CO_ID);
		assertEquals(DB_CO_ID, found.getId());
		
	}
	
	@Test
	public void testCreate() throws Exception {
		CulturalOffer culturalOffer = new CulturalOffer (NEW_CO_NAME, NEW_CO_LON, NEW_CO_LAT);
		CulturalOffer created = culturalOfferService.create(culturalOffer);
		
		verify(culturalOfferRepository, times(1)).findByName(NEW_CO_NAME);
		verify(culturalOfferRepository, times(1)).save(culturalOffer);
		assertEquals(NEW_CO_NAME, created.getName());
		
	}
	/*
	@Test(expected = java.lang.Exception.class)
	public void testCreate_GivenNameAlreadyExists() throws Exception {
		CulturalOffer culturalOffer = new CulturalOffer (DB_CO_NAME, DB_CO_DATE, DB_CO_LON, DB_CO_LAT);
		CulturalOffer created = culturalOfferService.create(culturalOffer);
		
		verify(culturalOfferRepository, times(1)).findByName(DB_CO_NAME);
		assertEquals(null, created);
	}
	*/
	
	@Test
	@Transactional
	public void testUpdate() throws Exception {
		CulturalOffer culturalOffer = new CulturalOffer (NEW_CO_NAME, NEW_CO_LON, NEW_CO_LAT);
		CulturalOffer created = culturalOfferService.update(culturalOffer, DB_CO_ID);
		
		verify(culturalOfferRepository, times(1)).findById(DB_CO_ID);
		assertEquals(NEW_CO_NAME, created.getName());
		
	}
	
	@Test
	@Transactional
	public void testDelete() throws Exception {
		
		culturalOfferService.delete(DB_CO_ID);
		CulturalOffer savedCO = new CulturalOffer (NEW_CO_NAME, NEW_CO_LON, NEW_CO_LAT);
		savedCO.setId(DB_CO_ID);
		
		verify(culturalOfferRepository, times(1)).findById(DB_CO_ID);
	}

}
