package app.geoMap.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import app.geoMap.model.CulturalOffer;
import static app.geoMap.constants.CulturalOfferConstants.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CulturalOfferRepositoryUnitTest {
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private CulturalOfferRepository culturalOfferRepository;
	
	@Before
	public void setUp() {
		entityManager.persist(new CulturalOffer(DB_CO_NAME, DB_CO_DATE, DB_CO_LON, DB_CO_LAT));
	}
	
	@Test
	public void testFindByName() {
		CulturalOffer found = culturalOfferRepository.findByName(DB_CO_NAME);
		assertEquals(DB_CO_NAME, found.getName());
	}
}
