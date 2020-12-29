package app.geoMap.controller;

import app.geoMap.dto.ImageDTO;
import app.geoMap.model.Image;
import app.geoMap.service.ImageService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static app.geoMap.constants.ImageConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@Sql("classpath:/data-h2.sql")
public class ImageControllerIntegrationTest {
	
	@Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ImageService imageService;


    @Test
    public void testGetAllCulturalContentCategories() {

        ResponseEntity<ImageDTO[]> responseEntity =
                restTemplate.getForEntity("/api/images",
                        ImageDTO[].class);

        ImageDTO[] images = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, images.length);
        assertEquals(DB_IMAGE_NAME, images[0].getName());
    }

    @Test
    public void testGetAllCulturalContentCategoriesPageable() {
        ResponseEntity<ImageDTO[]> responseEntity =
                restTemplate.getForEntity("/api/images/by-page?page=0&size=2",
                        ImageDTO[].class);

        ImageDTO[] images = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, images.length);
        assertEquals(DB_IMAGE_NAME, images[0].getName());
    }

    @Test
    public void testGetCulturalContentCategory() {
        ResponseEntity<ImageDTO> responseEntity =
                restTemplate.getForEntity("/api/images/1", ImageDTO.class);

        ImageDTO image = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(image);
        assertEquals(DB_IMAGE_NAME, image.getName());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateCulturalContentCategory() throws Exception {
        int size = imageService.findAll().size(); // broj slogova pre ubacivanja novog

        ResponseEntity<ImageDTO> responseEntity =
                restTemplate.postForEntity("/api/images",
                        new ImageDTO(null, DB_IMAGE_NAME),
                        ImageDTO.class);

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
    public void testUpdateCulturalContentCategory() throws Exception {
        ResponseEntity<ImageDTO> responseEntity =
                restTemplate.exchange("/api/cultural-content-category/1", HttpMethod.PUT,
                        new HttpEntity<ImageDTO>(new ImageDTO(DB_IMAGE_ID,NEW_IMAGE_NAME)),
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
    @Transactional
    @Rollback(true)
    public void testDeleteCulturalContentCategory() throws Exception {
        // ubacimo kategoriju koju cemo brisati
        Image image = imageService.create(new Image(NEW_IMAGE_NAME));
        // preuzmemo trenutni broj kategorija
        List<Image> images = imageService.findAll();
        int size = imageService.findAll().size();

        // poziv REST servisa za brisanje
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/images/" + image.getId(),
                        HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);

        // provera odgovora servera
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // mora biti jedan manje slog sada nego pre
        assertEquals(size - 1, imageService.findAll().size());
    }
}