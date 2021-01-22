package app.geoMap.service;

import app.geoMap.model.Image;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import javax.transaction.Transactional;

import static app.geoMap.constants.ImageConstants.*;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class ImageServiceIntegrationTest {
	@Autowired
    private  ImageService imageService;

    @Test
    public void testFindAllPageable() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Image> found = imageService.findAll(pageable);
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
    }
	
    @Test
    public void testFindAll() {
        List<Image> found = imageService.findAll();
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
    }

    @Test
    public void testFindById() {
    	Image found = imageService.findOne(DB_IMAGE_ID);
        assertEquals(DB_IMAGE_ID, found.getId());
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
    	Image image = new Image(NEW_IMAGE_NAME);
    	image.setId(NEW_IMAGE_ID);
        Image created = imageService.create(image);

        assertEquals(NEW_IMAGE_NAME, created.getName());
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        Image image = new Image(NEW_IMAGE_NAME);
        Image created = imageService.update(image,NEW_IMAGE_IDD);

        assertEquals(NEW_IMAGE_NAME, created.getName());
    }
    
    @Test
    @Transactional
    public void testDelete() throws Exception {
    	imageService.delete(NEW_IMAGE_IDD);

        Image savedUser = new Image(NEW_IMAGE_NAME);
        savedUser.setId(NEW_IMAGE_ID);
    }
}