package app.geoMap.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import app.geoMap.model.CulturalOffer;

import static app.geoMap.constants.CulturalOfferConstants.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class CulturalOfferRepositoryIntegrationTest {

	@Autowired
	private CulturalOfferRepository culturalOfferRepository;
	
	
	@Test
	public void testFindByName() {
		CulturalOffer found = culturalOfferRepository.findByName(DB_CO_NAME);
		assertEquals(DB_CO_NAME, found.getName());
		
		
	}
}
