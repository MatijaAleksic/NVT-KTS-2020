package app.geoMap.controller;

import app.geoMap.dto.ImageDTO;
import app.geoMap.dto.UserLoginDTO;
import app.geoMap.dto.UserTokenStateDTO;
import app.geoMap.model.Image;
import app.geoMap.repository.ImageRepository;
import app.geoMap.service.CustomUserDetailsService;
import app.geoMap.service.ImageService;
import  app.geoMap.controller.ImageDTOList;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.internal.MethodSorter;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static app.geoMap.constants.ImageConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

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
    
	@Autowired
	private ImageRepository imageRepository;

	private String accessToken;

	
	@Before
	public void loginAdmin() {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
				new UserLoginDTO("markoMarkovic@maildrop.cc", "MarkoMarkovic12"), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}
	
//	@Before
//	public void loginUser() {
//		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
//				new UserLoginDTO("peroPerovic@maildrop.cc", "PeroPerovic12"), UserTokenStateDTO.class);
//		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
//	}
	

    @Test
    @Order(1)
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
    @Order(2)
    public void BtestGetImage() {
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
    @Order(3)
    @Transactional
    @Rollback(true)
    public void CtestCreateImage() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<ImageDTO> httpEntity = new HttpEntity<>(new ImageDTO(null , NEW_IMAGE_NAME), headers);
        
        int size = imageService.findAll().size(); // broj slogova pre ubacivanja novog

//        ResponseEntity<ImageDTO> responseEntity =
//                restTemplate.postForEntity("/api/images",
//                        new ImageDTO(null, DB_IMAGE_NAME),
//                        ImageDTO.class);
        
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
    @Order(4)
    @Transactional
    @Rollback(true)
    public void DtestUpdateImage() throws Exception {
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
        assertEquals(DB_IMAGE_ID, image.getId());
        assertEquals(NEW_IMAGE_NAME, image.getName());

        // vracanje podatka na staru vrednost
        dbImage.setName(NEW_IMAGE_NAME);
        imageService.update(dbImage, dbImage.getId());
    }

    @Test
    @Order(5)
    @Transactional
    @Rollback(true)
    public void EtestDeleteImage() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<ImageDTO> httpEntity = new HttpEntity<>(headers);
        
        List<Image> images = imageService.findAll();
        int size = imageService.findAll().size();

        // poziv REST servisa za brisanje
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/images/1",
                        HttpMethod.DELETE, httpEntity, Void.class);

        // provera odgovora servera
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // mora biti jedan manje slog sada nego pre
        assertEquals(size - 1, imageService.findAll().size());
    }
}