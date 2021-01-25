package app.geoMap.service;

import app.geoMap.model.Rating;
import app.geoMap.model.User;

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

import static app.geoMap.constants.RatingConstants.*;
import static app.geoMap.constants.UserConstants.NEW_LAST_NAME;
import static app.geoMap.constants.UserConstants.NEW_NAME;
import static app.geoMap.constants.UserConstants.NEW_PASSWORD;
import static app.geoMap.constants.UserConstants.NEW_USER_EMAIL;
import static app.geoMap.constants.UserConstants.NEW_USER_NAME;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RatingServiceIntegrationTest {
	
	@Autowired
    private  RatingService ratingService;

	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
	    Page<Rating> found = ratingService.findAll(pageable);
	    assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
	}
	
    @Test
    public void testFindAll() {
        List<Rating> found = ratingService.findAll();
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
    }

    @Test
    public void testFindById() {
    	Rating found = ratingService.findOne(DB_RATING_ID);
        assertEquals(DB_RATING_ID, found.getId());
    }

    @Test
    public void testCreate() throws Exception {
    	Rating rating = new Rating(NEW_RATING_VALUE, new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL)); 
        rating.setId(NEW_RATING_ID);
    	Rating created = ratingService.create(rating);

        assertEquals(NEW_USER_EMAIL, created.getUser().getEmail());
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        Rating user = new Rating(NEW_RATING_VALUE, new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL));
        Rating created = ratingService.update(user,NEW_RATING_IDD);

        assertEquals(NEW_USER_EMAIL, created.getUser().getEmail());
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
    	ratingService.delete(NEW_RATING_IDD);

        Rating savedRating = new Rating(NEW_RATING_VALUE, new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL));
        savedRating.setId(NEW_RATING_ID);
    }

}
