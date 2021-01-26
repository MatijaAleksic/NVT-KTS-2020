package app.geoMap.controller;

import app.geoMap.dto.CulturalOfferDTO;
import app.geoMap.dto.UserLoginDTO;
import app.geoMap.dto.UserTokenStateDTO;
import app.geoMap.model.CulturalOffer;

import static app.geoMap.constants.CulturalOfferConstants.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import app.geoMap.service.CulturalOfferService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired 
	private CulturalOfferService culturalOfferService;
	
	private String accessToken;
	
	@Before
	public void login() {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in", 
				new UserLoginDTO("markoMarkovic@maildrop.cc", "MarkoMarkovic12"), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}
	
	
	@Test
	@Order(1)
	public void testGetAllCulturalOffers() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		
		ResponseEntity<CulturalOfferDTO[]> responseEntity =
				restTemplate.exchange("/api/cultural-offers", HttpMethod.GET, httpEntity, CulturalOfferDTO[].class);
		
		CulturalOfferDTO[] culturalOffers = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, culturalOffers.length);
		assertEquals(DB_CO_NAME, culturalOffers[0].getName());
		
	}
	
	@Test
	@Order(2)
	public void testGetCulturalOffer() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		
		ResponseEntity<CulturalOfferDTO> responseEntity =
				restTemplate.exchange("/api/cultural-offers/1", HttpMethod.GET, httpEntity, CulturalOfferDTO.class);
		
		CulturalOfferDTO culturalOffer = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(culturalOffer);
		assertEquals(DB_CO_NAME, culturalOffer.getName());
		
	}
	
	
	@Test
	@Order(3)
	@Transactional
    @Rollback(true)
	public void testCreateCulturalOffer() throws Exception {
		int size = culturalOfferService.findAll().size();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<CulturalOfferDTO> httpEntity = new HttpEntity<>(new CulturalOfferDTO(null, NEW_CO_NAME, NEW_CO_DATE, NEW_CO_LON, NEW_CO_LAT), headers);
		
		ResponseEntity<CulturalOfferDTO> responseEntity =
				restTemplate.exchange("/api/cultural-offers", HttpMethod.POST, httpEntity, CulturalOfferDTO.class);
		
		CulturalOfferDTO culturalOffer = responseEntity.getBody();
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(culturalOffer);
		assertEquals(NEW_CO_NAME, culturalOffer.getName());
		
		List<CulturalOffer> culturalOffers  = culturalOfferService.findAll();
		assertEquals(size + 1, culturalOffers.size());
		assertEquals(NEW_CO_NAME, culturalOffers.get(culturalOffers.size()-1).getName());
		
		culturalOfferService.delete(culturalOffer.getId());
		
	}
	
	@Test
	@Order(4)
	@Transactional
    @Rollback(true)
	public void testUpdateCulturalOffer() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<CulturalOfferDTO> httpEntity = new HttpEntity<>(new CulturalOfferDTO(DB_CO_ID, NEW_CO_NAME, NEW_CO_DATE, NEW_CO_LON, NEW_CO_LAT), headers);
		
		ResponseEntity<CulturalOfferDTO> responseEntity =
				restTemplate.exchange("/api/cultural-offers/1" , HttpMethod.PUT, httpEntity, CulturalOfferDTO.class);
		
		CulturalOfferDTO culturalOffer = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(culturalOffer);
		assertEquals(DB_CO_ID, culturalOffer.getId());
		assertEquals(NEW_CO_NAME, culturalOffer.getName());
		
		CulturalOffer dbCO = culturalOfferService.findOne(DB_CO_ID);
		assertEquals(DB_CO_ID, culturalOffer.getId());
		assertEquals(NEW_CO_NAME, culturalOffer.getName());
		
		dbCO.setName(NEW_CO_NAME);
		dbCO.setCreationDate(NEW_CO_DATE);
		dbCO.setLongitude(DB_CO_LON);
		dbCO.setLatitude(DB_CO_LAT);
		
		culturalOfferService.update(dbCO, dbCO.getId());
		
	}
	
	@Test
	@Order(5)
	@Transactional
    @Rollback(true)
	public void testDeleteCulturalOffer() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<CulturalOfferDTO> httpEntity = new HttpEntity<>(headers);
		
		CulturalOffer culturalOffer = culturalOfferService.create(new CulturalOffer(NEW_CO_NAME, NEW_CO_DATE, NEW_CO_LON, NEW_CO_LAT));
		
		List<CulturalOffer> culturalOffers = culturalOfferService.findAll();
		int size = culturalOfferService.findAll().size();
		
		ResponseEntity<Void> responseEntity =
				restTemplate.exchange("/api/cultural-offers/" + culturalOffer.getId(),
				HttpMethod.DELETE, httpEntity, Void.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(size - 1, culturalOfferService.findAll().size());
		
		
	}
	
	
}
