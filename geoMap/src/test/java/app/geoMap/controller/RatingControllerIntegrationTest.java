package app.geoMap.controller;

import app.geoMap.dto.RatingDTO;
import app.geoMap.dto.UserLoginDTO;
import app.geoMap.dto.UserTokenStateDTO;
import app.geoMap.model.Rating;
import app.geoMap.service.CustomUserDetailsService;
import app.geoMap.service.RatingService;

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

import static app.geoMap.constants.RatingConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RatingControllerIntegrationTest {
	
	@Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RatingService ratingService;
    
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
    public void AtestGetAllRatings() {
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

        ResponseEntity<RatingDTO[]> responseEntity =
                restTemplate.exchange("/api/ratings",HttpMethod.GET, httpEntity, RatingDTO[].class);

        RatingDTO[] ratings = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, ratings.length);
        assertEquals(DB_RATING_ID, ratings[0].getId());
    }
    
    @Test
    public void BtestGetAllRatingsPageable() {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
        
        ResponseEntity<RatingDTO[]> responseEntity =
                restTemplate.exchange("/api/ratings/by-page?page=0&size=2",HttpMethod.GET, httpEntity, RatingDTO[].class);

        RatingDTO[] ratings = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, ratings.length);
        assertEquals(DB_RATING_ID, ratings[0].getId());
    }

    @Test
    public void CtestGetRating() {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
        
        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/api/ratings/1",HttpMethod.GET, httpEntity, RatingDTO.class);

        RatingDTO rating = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(rating);
        assertEquals(DB_RATING_ID, rating.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void DtestCreateRating() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        HttpEntity<RatingDTO> httpEntity = new HttpEntity<>(new RatingDTO(null, NEW_RATING_VALUE), headers);
        
        int size = ratingService.findAll().size(); // broj slogova pre ubacivanja novog

        
        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/api/ratings",HttpMethod.POST, httpEntity, RatingDTO.class);

        // provera odgovora servera
        RatingDTO rating = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(rating);
        assertEquals(NEW_RATING_VALUE, rating.getValue(),0);

        List<Rating> ratings = ratingService.findAll();
        assertEquals(size + 1, ratings.size()); // mora biti jedan vise slog sada nego pre
        // poslednja kategorija u listi treba da bude nova kategorija koja je ubacena u testu
        assertEquals(NEW_RATING_VALUE, ratings.get(ratings.size()-1).getValue(),0);

        // uklanjamo dodatu kategoriju
        ratingService.delete(rating.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void EtestUpdateRating() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<RatingDTO> httpEntity = new HttpEntity<>(new RatingDTO(null, NEW_RATING_VALUE), headers);
        
        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/api/ratings/1", HttpMethod.PUT,httpEntity, RatingDTO.class);

        RatingDTO rating = responseEntity.getBody();

        // provera odgovora servera
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(rating);
        assertEquals(DB_RATING_ID, rating.getId());
        assertEquals(NEW_RATING_VALUE, rating.getValue(), 0);

        // provera da li je izmenjen slog u bazi
        Rating dbRating = ratingService.findOne(DB_RATING_ID);
        assertEquals(DB_RATING_ID, dbRating.getId());
        assertEquals(NEW_RATING_VALUE, dbRating.getValue(), 0);

        // vracanje podatka na staru vrednost
        dbRating.setValue(NEW_RATING_VALUE);
        ratingService.update(dbRating, dbRating.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void FtestDeleteRating() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<RatingDTO> httpEntity = new HttpEntity<>(headers);
        
        int size = ratingService.findAll().size();

        // poziv REST servisa za brisanje
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/ratings/1",
                		HttpMethod.DELETE,httpEntity , Void.class);

        // provera odgovora servera
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // mora biti jedan manje slog sada nego pre
        assertEquals(size - 1, ratingService.findAll().size());
    }

}
