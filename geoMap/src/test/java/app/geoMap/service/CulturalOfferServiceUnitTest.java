package app.geoMap.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import app.geoMap.model.CulturalOffer;
import app.geoMap.repository.CulturalOfferRepository;

import static app.geoMap.constants.CulturalOfferConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferServiceUnitTest {
	
	@Autowired
	private CulturalOfferService culturalOfferService;
	
	
	@Autowired
	private CulturalOfferRepository culturalOfferRepository;
	
	@Before
	public void setUp() {
		List<CulturalOffer> culturalOffers = new ArrayList<>();
		culturalOffers.add(new CulturalOffer(NEW_CO_NAME, NEW_CO_DATE, NEW_CO_LON, NEW_CO_LAT));
		
		
	}
	
	@Test
	public void testFindAll() {
		
	}
	
	@Test
	public void testFindAllPageable() {
		
	}
	
	@Test
	public void testFindOne() {
		
	}
	
	@Test
	public void testCreate() {
		
		
	}
	
	@Test
	public void testUpdate() {
		
		
	}
	
	@Test
	public void testDelete() {
		
		
		
	}

}
