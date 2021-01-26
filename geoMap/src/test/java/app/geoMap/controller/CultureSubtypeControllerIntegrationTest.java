package app.geoMap.controller;

import static app.geoMap.constants.CultureSubtypeConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import app.geoMap.dto.CultureSubtypeDTO;
import app.geoMap.dto.UserLoginDTO;
import app.geoMap.dto.UserTokenStateDTO;
import app.geoMap.model.CultureSubtype;
import app.geoMap.service.CultureSubtypeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CultureSubtypeControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CultureSubtypeService cultureSubtypeService;

	private String accessToken;

	
	@Before
	public void login() {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in", 
				new UserLoginDTO("markoMarkovic@maildrop.cc", "MarkoMarkovic12"), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}


	@Test
	public void AtestGetAllCultureSubtypes() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		

		ResponseEntity<CultureSubtypeDTO[]> responseEntity =
				restTemplate.exchange("/api/culture_subtype", HttpMethod.GET, httpEntity, CultureSubtypeDTO[].class);

		CultureSubtypeDTO[] cultureSubtypes = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(FIND_ALL_SUBTYPES, cultureSubtypes.length);
		assertEquals(DB_SUBTYPE, cultureSubtypes[0].getName());



	}

	@Test 
	public void BtestGetCultureSubtype() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		
		ResponseEntity<CultureSubtypeDTO> responseEntity = 
				restTemplate.exchange("/api/culture_subtype/1", HttpMethod.GET, httpEntity, CultureSubtypeDTO.class);

		CultureSubtypeDTO cultureSubtype = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(cultureSubtype);
		assertEquals(DB_SUBTYPE, cultureSubtype.getName());

	}

	@Test
    @Transactional
    @Rollback(true)
	public void CtestCreateCultureSubtype() throws Exception {
		
		int size = cultureSubtypeService.findAll().size();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<CultureSubtypeDTO> httpEntity = new HttpEntity<>(new CultureSubtypeDTO(null, NEW_SUBTYPE), headers);
		

		ResponseEntity<CultureSubtypeDTO> responseEntity = restTemplate.exchange("/api/culture_subtype"
				,HttpMethod.POST, httpEntity, CultureSubtypeDTO.class);

		CultureSubtypeDTO cultureSubtype = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(cultureSubtype);
		assertEquals(NEW_SUBTYPE, cultureSubtype.getName());

		List<CultureSubtype> cultureSubtypes = cultureSubtypeService.findAll();
		assertEquals(size + 1, cultureSubtypes.size());

		assertEquals(NEW_SUBTYPE, cultureSubtypes.get(cultureSubtypes.size()-1).getName());

		cultureSubtypeService.delete(cultureSubtype.getId());	

	}

	@Test
    @Transactional
    @Rollback(true)
	public void DtestUpdateCultureSubtype() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<CultureSubtypeDTO> httpEntity = new HttpEntity<>(new CultureSubtypeDTO(null, NEW_SUBTYPE),headers);
		
		ResponseEntity<CultureSubtypeDTO> responseEntity = restTemplate.exchange("/api/culture_subtype/1",  
				HttpMethod.PUT, httpEntity, CultureSubtypeDTO.class);

		CultureSubtypeDTO cultureSubtype = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(cultureSubtype);
		assertEquals(DB_SUBTYPE_ID, cultureSubtype.getId());
		assertEquals(NEW_SUBTYPE, cultureSubtype.getName());

		CultureSubtype dbCultureSubtype = cultureSubtypeService.findOne(DB_SUBTYPE_ID);
		assertEquals(DB_SUBTYPE_ID, cultureSubtype.getId());
		assertEquals(NEW_SUBTYPE, cultureSubtype.getName());

		dbCultureSubtype.setName(DB_SUBTYPE);
		cultureSubtypeService.update(dbCultureSubtype, dbCultureSubtype.getId());


	}

	@Test
    @Transactional
    @Rollback(true)
	public void EtestDeleteCultureSubtype() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<CultureSubtypeDTO> httpEntity = new HttpEntity<>(headers);
		
		//CultureSubtype cultureSubtype = cultureSubtypeService.create(new CultureSubtype(NEW_SUBTYPE));

		List<CultureSubtype> cultureSubtypes = cultureSubtypeService.findAll();
		int size = cultureSubtypeService.findAll().size();

		ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/culture_subtype/1",
				HttpMethod.DELETE, httpEntity, Void.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(size - 1, cultureSubtypeService.findAll().size());


	}
	/*
	
	 */
}