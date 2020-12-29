package app.geoMap.repository;

import app.geoMap.model.Rating;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static app.geoMap.constants.RatingConstants.*;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
public class RatingRepositoryIntegrationTest {

	
	@Autowired
    private RatingRepository ratingRepository;

	@Sql("classpath:/data-h2.sql")
    @Test
    public void testfindByUser_Email() {
        Rating found = ratingRepository.findByUser_Email(DB_USER_EMAIL);
        assertEquals(DB_USER_EMAIL, found.getUser().getEmail());
    }
	
	
	@Sql("classpath:/data-h2.sql")
    @Test
    public void testfindByUserEmail() {
        Rating found = ratingRepository.findByUser_Id(DB_USER_ID);
        assertEquals(DB_USER_ID, found.getUser().getId());
    }
}
