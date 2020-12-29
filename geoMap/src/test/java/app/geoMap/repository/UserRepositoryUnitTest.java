package app.geoMap.repository;

import app.geoMap.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static app.geoMap.constants.UserConstants.NEW_USER_EMAIL;
import static app.geoMap.constants.UserConstants.NEW_USER_NAME;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryUnitTest {
	
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Before
    public void setUp() {
        entityManager.persist(new User("matija", "aleksic", "matija123", "aleksic123", "matijaAleksic@maildrop.cc" ));
    }

    @Test
    public void testFindByEmail() {
        User found = userRepository.findByEmail(NEW_USER_EMAIL);
        assertEquals(NEW_USER_EMAIL, found.getEmail());
    }
    
    @Test
    public void testFindUserName() {
        User found = userRepository.findByUserName(NEW_USER_NAME);
        assertEquals(NEW_USER_NAME, found.getUserName());
    }

}


