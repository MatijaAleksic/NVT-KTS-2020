package app.geoMap.controller;

import app.geoMap.dto.ImageDTO;
import app.geoMap.dto.UserLoginDTO;
import app.geoMap.dto.UserTokenStateDTO;
import app.geoMap.model.Image;
import app.geoMap.service.CustomUserDetailsService;
import app.geoMap.service.ImageService;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static app.geoMap.constants.ImageConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImageControllerIntegrationTest {
	
	@Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ImageService imageService;
    
    @Autowired
    CustomUserDetailsService userDetailsService;
    

	private String accessToken;

	
	@Before
	public void loginAdmin() {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
				new UserLoginDTO("markoMarkovic@maildrop.cc", "MarkoMarkovic12"), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}	

    @Test
    public void AtestGetAllImages() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

        ResponseEntity<ImageDTO[]> responseEntity =
                restTemplate.exchange("/api/images",HttpMethod.GET, httpEntity, ImageDTO[].class);
        
        ImageDTO[] images = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, images.length);
        assertEquals(DB_IMAGE_NAME, images[0].getName());
    }
    
    @Test
    public void BtestGetAllImagesPageable() {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
        
        ResponseEntity<ImageDTO[]> responseEntity =
                restTemplate.exchange("/api/images/by-page?page=0&size=2",HttpMethod.GET, httpEntity, ImageDTO[].class);

        ImageDTO[] images = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, images.length);
        assertEquals(DB_IMAGE_ID, images[0].getId());
    }

    @Test
    public void CtestGetImage() {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
        
        ResponseEntity<ImageDTO> responseEntity =
                restTemplate.exchange("/api/images/1",HttpMethod.GET, httpEntity, ImageDTO.class);

        ImageDTO image = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(image);
        assertEquals(DB_IMAGE_NAME, image.getName());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void DtestCreateImage() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<ImageDTO> httpEntity = new HttpEntity<>(new ImageDTO(null , NEW_IMAGE_NAME), headers);
        
        int size = imageService.findAll().size();
        
        ResponseEntity<ImageDTO> responseEntity =
                restTemplate.exchange("/api/images",HttpMethod.POST, httpEntity, ImageDTO.class);

        // provera odgovora servera
        ImageDTO image = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(image);
        assertEquals(NEW_IMAGE_NAME, image.getName());

        List<Image> images = imageService.findAll();
        assertEquals(size + 1, images.size()); // mora biti jedan vise slog sada nego pre
        // poslednja kategorija u listi treba da bude nova kategorija koja je ubacena u testu
        assertEquals(NEW_IMAGE_NAME, images.get(images.size()-1).getName());

        // uklanjamo dodatu kategoriju
        imageService.delete(image.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void EtestUpdateImage() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<ImageDTO> httpEntity = new HttpEntity<>(new ImageDTO(null,NEW_IMAGE_NAME), headers);
       
        ResponseEntity<ImageDTO> responseEntity =
                restTemplate.exchange("/api/images/1", HttpMethod.PUT,httpEntity,
                        ImageDTO.class);

        ImageDTO image = responseEntity.getBody();

        // provera odgovora servera
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(image);
        assertEquals(DB_IMAGE_ID, image.getId());
        assertEquals(NEW_IMAGE_NAME, image.getName());

        // provera da li je izmenjen slog u bazi
        Image dbImage = imageService.findOne(DB_IMAGE_ID);
        assertEquals(DB_IMAGE_ID, dbImage.getId());
        assertEquals(NEW_IMAGE_NAME, dbImage.getName());

        // vracanje podatka na staru vrednost
        dbImage.setName(NEW_IMAGE_NAME);
        imageService.update(dbImage, dbImage.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void FtestDeleteImage() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<ImageDTO> httpEntity = new HttpEntity<>(headers);
        
        int size = imageService.findAll().size();

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/images/1",
                        HttpMethod.DELETE, httpEntity, Void.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(size - 1, imageService.findAll().size());
    }
}