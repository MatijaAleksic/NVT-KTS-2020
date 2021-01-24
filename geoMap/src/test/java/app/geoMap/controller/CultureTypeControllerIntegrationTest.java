package app.geoMap.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import app.geoMap.dto.UserLoginDTO;
import app.geoMap.dto.UserTokenStateDTO;
import app.geoMap.model.CultureType;
import app.geoMap.dto.CultureTypeDTO;
import app.geoMap.service.CultureTypeService;
import static app.geoMap.constants.CultureTypeConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CultureTypeControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired 
	private CultureTypeService cultureTypeService;
	
	private String accessToken;
	/*
	@Before
	public void login(String username, String password) {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("auth/log-in", new UserLoginDTO(username,password), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}
	*/
	
	@Test
	public void testGetAllCultureTypes() {
		/*
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		*/
		ResponseEntity<CultureTypeDTO[]> responseEntity =
				restTemplate.getForEntity("/api/cultural-type", CultureTypeDTO[].class );
		
		CultureTypeDTO[] cultureTypes = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, cultureTypes.length);
		assertEquals(DB_TYPE, cultureTypes[0].getName());
			
	}
	
	@Test
	public void testGetAllCultureTypesPageable() {
		/*
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		*/
		ResponseEntity<CultureTypeDTO[]> responseEntity = restTemplate.getForEntity("/api/cultural-type/by-page?page=0&size=2",  CultureTypeDTO[].class );
		
		CultureTypeDTO[] cultureTypes = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, cultureTypes.length);
		assertEquals(DB_TYPE, cultureTypes[0].getName());
	}
	
	@Test
	public void testGetCultureType() {
		/*
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		*/
		ResponseEntity<CultureTypeDTO> responseEntity = restTemplate.getForEntity("/api/cultural-type/"+ TYPE_ID, CultureTypeDTO.class );
		
		CultureTypeDTO cultureType = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(cultureType);
		assertEquals(DB_TYPE, cultureType.getName());
		
	}
	
	@Test
    @Transactional
    @Rollback(true)
	public void testCreateCultureType() throws Exception {
		int size = cultureTypeService.findAll().size();
		/*
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		*/
		ResponseEntity<CultureTypeDTO> responseEntity = restTemplate.postForEntity("/api/cultural-type", new CultureTypeDTO(null, NEW_TYPE), CultureTypeDTO.class );
		
		CultureTypeDTO cultureType = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(cultureType);
		assertEquals(NEW_TYPE, cultureType.getName());
		
		List<CultureType> cultureTypes = cultureTypeService.findAll();
		assertEquals(size + 1, cultureTypes.size());
		// poslednja kategorija u listi treba da bude nova kategorija koja je ubacena u testu
		assertEquals(NEW_TYPE, cultureTypes.get(cultureTypes.size()-1).getName());
		
		cultureTypeService.delete(cultureType.getId());
		
	}
	
	@Test
    @Transactional
    @Rollback(true)
	public void testUpdateCultureType() throws Exception {
		/*
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		*/
		ResponseEntity<CultureTypeDTO> responseEntity = restTemplate.exchange("/api/cultural-type/"+ TYPE_ID, HttpMethod.PUT, 
				new HttpEntity<CultureTypeDTO>(new CultureTypeDTO(DB_TYPE_ID, NEW_TYPE)), CultureTypeDTO.class );
		
		CultureTypeDTO cultureType = responseEntity.getBody();
		//provera odgovora servera
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(cultureType);
		assertEquals(DB_TYPE_ID, cultureType.getId());
		assertEquals(NEW_TYPE, cultureType.getName());
		//provera da li je zimenjen slog u bazi
		CultureType dbCultureType = cultureTypeService.findOne(DB_TYPE_ID);
		assertEquals(DB_TYPE_ID, cultureType.getId());
		assertEquals(NEW_TYPE, cultureType.getName());
		
		//vracanje podataka na staru vrednost
		dbCultureType.setName(NEW_TYPE);
		cultureTypeService.update(dbCultureType, dbCultureType.getId());
		
		
	}
	
	@Test
    @Transactional
    @Rollback(true)
	public void testDeleteCultureType() throws Exception {
		CultureType cultureType = cultureTypeService.create(new CultureType(NEW_TYPE));
		
		List<CultureType> cultureTypes = cultureTypeService.findAll();
		int size =  cultureTypeService.findAll().size();
		
		//poziv REST servisa za brisanje
		ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/cultural-type/" + cultureType.getId(),
				HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(size - 1, cultureTypeService.findAll().size());
		
	}
	
	
	
	
	
	

}
