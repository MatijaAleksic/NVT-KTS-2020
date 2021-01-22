package app.geoMap.repository;

import app.geoMap.model.Rating;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static app.geoMap.constants.RatingConstants.*;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class RatingRepositoryIntegrationTest {

	
	@Autowired
    private RatingRepository ratingRepository;

    @Test
    public void testfindByUser_Email() {
        Rating found = ratingRepository.findByUser_Email(DB_USER_EMAIL);
        assertEquals(DB_USER_EMAIL, found.getUser().getEmail());
    }
	
	
    @Test
    public void testfindByUser_Id() {
        Rating found = ratingRepository.findByUser_Id(DB_USER_ID);
        assertEquals(DB_USER_ID, found.getUser().getId());
    }
}
