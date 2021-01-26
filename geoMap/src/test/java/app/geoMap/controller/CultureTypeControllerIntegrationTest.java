package app.geoMap.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.annotation.Order;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CultureTypeControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired 
	private CultureTypeService cultureTypeService;
	
	private String accessToken;
	
	@Before
	public void login() {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in", 
				new UserLoginDTO("markoMarkovic@maildrop.cc", "MarkoMarkovic12"), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}
	
	
	@Test
	@Order(1)
	public void AtestGetAllCultureTypes() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		
		ResponseEntity<CultureTypeDTO[]> responseEntity =
				restTemplate.exchange("/api/cultural-type",HttpMethod.GET, httpEntity, CultureTypeDTO[].class );
		
		CultureTypeDTO[] cultureTypes = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, cultureTypes.length); //_DB
		//assertEquals(DB_TYPE, cultureTypes[0].getName());
			
	}
	
	@Test
	@Order(2)
	public void BtestGetAllCultureTypesPageable() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		//headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		
		ResponseEntity<CultureTypeDTO[]> responseEntity = 
				restTemplate.exchange("/api/cultural-type/by-page?page=0&size=2", HttpMethod.GET, httpEntity,  CultureTypeDTO[].class );
		
		CultureTypeDTO[] cultureTypes = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, cultureTypes.length); //_D
		assertEquals(DB_TYPE, cultureTypes[0].getName());
	}
	
	@Test
	@Order(3)
	public void CtestGetCultureType() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		//headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		
		ResponseEntity<CultureTypeDTO> responseEntity = 
				restTemplate.exchange("/api/cultural-type/1", HttpMethod.GET, httpEntity, CultureTypeDTO.class );
		
		CultureTypeDTO cultureType = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(cultureType);
		assertEquals(DB_TYPE, cultureType.getName());
		
	}
	
	@Test
	@Order(4)
    @Transactional
    @Rollback(true)
	public void DtestCreateCultureType() throws Exception {
		int size = cultureTypeService.findAll().size();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<CultureTypeDTO> httpEntity = new HttpEntity<>(new CultureTypeDTO(null, NEW_TYPE),headers);
		
		ResponseEntity<CultureTypeDTO> responseEntity = 
				restTemplate.exchange("/api/cultural-type", HttpMethod.POST, httpEntity, CultureTypeDTO.class );
		
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
	@Order(5)
    @Transactional
    @Rollback(true)
	public void EtestUpdateCultureType() throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		//headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CultureTypeDTO> httpEntity = new HttpEntity<>(new CultureTypeDTO(null, NEW_TYPE), headers);
		
		ResponseEntity<CultureTypeDTO> responseEntity = 
				restTemplate.exchange("/api/cultural-type/1", HttpMethod.PUT, httpEntity, CultureTypeDTO.class );
		
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
	@Order(6)
    @Transactional
    @Rollback(true)
	public void FtestDeleteCultureType() throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<CultureTypeDTO> httpEntity = new HttpEntity<>(headers);
		
		//CultureType cultureType = cultureTypeService.create(new CultureType(NEW_TYPE));
		
		List<CultureType> cultureTypes = cultureTypeService.findAll();
		int size =  cultureTypeService.findAll().size();
		
		//poziv REST servisa za brisanje
		ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/cultural-type/1" ,
				HttpMethod.DELETE, httpEntity, Void.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(size - 1, cultureTypeService.findAll().size());
		
	}
	
	
	
	
	
	

}
