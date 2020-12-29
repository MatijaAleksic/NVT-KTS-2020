package app.geoMap.controller;

import app.geoMap.dto.RatingDTO;
import app.geoMap.model.Rating;
import app.geoMap.model.User;
import app.geoMap.service.RatingService;

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

import static app.geoMap.constants.RatingConstants.*;
import static app.geoMap.constants.UserConstants.DB_USER_ID;
import static app.geoMap.constants.UserConstants.NEW_LAST_NAME;
import static app.geoMap.constants.UserConstants.NEW_NAME;
import static app.geoMap.constants.UserConstants.NEW_PASSWORD;
import static app.geoMap.constants.UserConstants.NEW_USER_EMAIL;
import static app.geoMap.constants.UserConstants.NEW_USER_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RatingControllerIntegrationTest {
	
	@Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RatingService ratingService;
    
    
    @Sql("classpath:/data-h2.sql")
    @Test
    public void testGetAllRatingsPageable() {
        ResponseEntity<RatingDTO[]> responseEntity =
                restTemplate.getForEntity("/api/users/by-page?page=0&size=2",
                        RatingDTO[].class);

        RatingDTO[] users = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, users.length);
        assertEquals(DB_USER_EMAIL, users[0].getUser().getEmail());
    }
    
    
    @Test
    public void testGetAllRatings() {

        ResponseEntity<RatingDTO[]> responseEntity =
                restTemplate.getForEntity("/api/users",
                        RatingDTO[].class);

        RatingDTO[] ratings = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, ratings.length);
        assertEquals(DB_USER_EMAIL, ratings[0].getUser().getEmail());
    }

    @Test
    public void testGetCulturalContentCategory() {
        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.getForEntity("/api/users/1", RatingDTO.class);

        RatingDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(user);
        assertEquals(DB_USER_EMAIL, user.getUser().getEmail());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateRating() throws Exception {
        int size = ratingService.findAll().size(); // broj slogova pre ubacivanja novog

        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.postForEntity("/api/users",
                        new RatingDTO(null, DB_NEW_RATING_VALUE),
                        RatingDTO.class);

        // provera odgovora servera
        RatingDTO rating = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(rating);
        assertEquals(DB_NEW_USER_EMAIL ,rating.getUser().getEmail());

        List<Rating> ratings = ratingService.findAll();
        assertEquals(size + 1, ratings.size()); // mora biti jedan vise slog sada nego pre
        // poslednja kategorija u listi treba da bude nova kategorija koja je ubacena u testu
        assertEquals(DB_NEW_USER_EMAIL, ratings.get(ratings.size()-1).getUser().getEmail());

        // uklanjamo dodatu kategoriju
        ratingService.delete(rating.getId());
    }

//    @Test
//    @Transactional
//    @Rollback(true)
//    public void testUpdateRating() throws Exception {
//        ResponseEntity<RatingDTO> responseEntity =
//                restTemplate.exchange("/api/cultural-content-category/1", HttpMethod.PUT,
//                        new HttpEntity<RatingDTO>(new RatingDTO(DB_RATING_ID, DB_RATING_VALUE)));
//
//        RatingDTO rating = responseEntity.getBody();
//
//        // provera odgovora servera
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(rating);
//        assertEquals(DB_RATING_ID, rating.getId());
//
//        // provera da li je izmenjen slog u bazi
//        Rating dbUser = ratingService.findOne(DB_RATING_ID);
//        assertEquals(DB_RATING_ID, rating.getId());
//    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteRating() throws Exception {
        // ubacimo kategoriju koju cemo brisati
        Rating user = ratingService.create(new Rating(DB_RATING_ID, new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL)));
        // preuzmemo trenutni broj kategorija
        List<Rating> users = ratingService.findAll();
        int size = ratingService.findAll().size();

        // poziv REST servisa za brisanje
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/users/" + user.getId(),
                        HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);

        // provera odgovora servera
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // mora biti jedan manje slog sada nego pre
        assertEquals(size - 1, ratingService.findAll().size());
    }

}
