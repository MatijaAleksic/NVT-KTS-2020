package app.geoMap.repository;

import app.geoMap.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.*;


import static app.geoMap.constants.UserConstants.DB_USER_EMAIL;
import static app.geoMap.constants.UserConstants.DB_USER_NAME;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class UserRepositoryIntegrationTest {
	
	@Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        User found = userRepository.findByEmail(DB_USER_EMAIL);
        assertEquals(DB_USER_EMAIL, found.getEmail());
    }
    
    @Test
    public void testFindUserName() {
        User found = userRepository.findByUsername(DB_USER_NAME);
        assertEquals(DB_USER_NAME, found.getUserName());
    }
}
