package app.geoMap.repository;

import static app.geoMap.constants.RatingConstants.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import app.geoMap.model.Rating;
import app.geoMap.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RatingRepositoryUnitTest {
	
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RatingRepository ratingRepository;


    @Before
    public void setUp() {
    	User user = new User("matija", "aleksic", "matija123", "aleksic123", "matijaAleksic@maildrop.cc");
        entityManager.persist(new Rating(1, user));
    }

    @Test
    public void testfindByUser_Email() {
        Rating found = ratingRepository.findByUser_Email("matijaAleksic@maildrop.cc");
        assertEquals("matijaAleksic@maildrop.cc", found.getUser().getEmail());
    }
    
    @Test
    public void testfindByUser_Id() {
    	Rating found = ratingRepository.findByUser_Id(DB_USER_ID);
        assertEquals(DB_USER_ID, found.getUser().getId());
    }

}
