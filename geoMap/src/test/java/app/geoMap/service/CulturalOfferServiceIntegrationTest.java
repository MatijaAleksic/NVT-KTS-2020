package app.geoMap.service;

import static app.geoMap.constants.CulturalOfferConstants.*;
import static org.junit.Assert.assertEquals;

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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferServiceIntegrationTest {
	
	@Autowired
	private CulturalOfferService culturalOfferService;
	
	
	
	@Test
	public void testFindAll() {
		List<CulturalOffer> foundCulturalOffer = culturalOfferService.findAll();
		
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, foundCulturalOffer.size());
		
	}
	
	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<CulturalOffer> foundCultureOffer = culturalOfferService.findAll(pageable);
		
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, foundCultureOffer.getNumberOfElements());
	}
	
	@Test
	public void testFindOne() {
		CulturalOffer found = culturalOfferService.findOne(DB_CO_ID);
		
		assertEquals(DB_CO_ID, found.getId());
		
	}
	
	@Test
	public void testCreate() throws Exception {
		CulturalOffer culturalOffer = new CulturalOffer (NEW_CO_NAME, NEW_CO_DATE, NEW_CO_LON, NEW_CO_LAT);
		culturalOffer.setId(NEW_CO_ID);
		CulturalOffer created = culturalOfferService.create(culturalOffer);
		
		assertEquals(NEW_CO_NAME, created.getName());
		
	}
	/*
	@Test
	public void testCreate_GivenNameAlreadyExists() throws Exception {
		CulturalOffer culturalOffer = new CulturalOffer (NEW_CO_NAME, NEW_CO_DATE, NEW_CO_LON, NEW_CO_LAT);
		CulturalOffer created = culturalOfferService.create(culturalOffer);
		
		assertEquals(null, created);
	}
	*/
	
	@Test
	@Transactional
	public void testUpdate() throws Exception {
		CulturalOffer culturalOffer = new CulturalOffer (NEW_CO_NAME, NEW_CO_DATE, NEW_CO_LON, NEW_CO_LAT);
		CulturalOffer created = culturalOfferService.update(culturalOffer, DB_CO_ID);
		
		assertEquals(NEW_CO_NAME, created.getName());
		
	}
	
	@Test
	@Transactional
	public void testDelete() throws Exception {
		
		culturalOfferService.delete(DB_CO_ID);
		CulturalOffer savedCO = new CulturalOffer (NEW_CO_NAME, NEW_CO_DATE, NEW_CO_LON, NEW_CO_LAT);
		savedCO.setId(DB_CO_ID);
		
	}

}
