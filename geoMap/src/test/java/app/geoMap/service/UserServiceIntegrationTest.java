package app.geoMap.service;

import app.geoMap.model.User;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static app.geoMap.constants.UserConstants.*;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest {
	@Autowired
    private  UserService userService;

	@Sql("classpath:/data-h2.sql")
    @Test
    public void testFindAllPageable() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<User> found = userService.findAll(pageable);
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
    }
	
    @Test
    public void testFindAll() {
        List<User> found = userService.findAll();
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
    }

    @Test
    public void testFindById() {
    	User found = userService.findOne(DB_USER_ID);
        assertEquals(DB_USER_ID, found.getId());
    }

    @Test
    @Order(1)
    public void testCreate() throws Exception {
    	User user = new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
        user.setId(NEW_ID);
    	User created = userService.create(user);
        assertEquals(NEW_USER_EMAIL, created.getEmail());
    }

    @Test
    @Order(2)
    public void testUpdate() throws Exception {
        User user = new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
        User created = userService.update(user,NEW_IDD);

        assertEquals(NEW_USER_EMAIL, created.getEmail());
    }

    @Test
    @Order(3)
    public void testDelete() throws Exception {
    	userService.delete(NEW_ID);

        User savedUser = new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
        savedUser.setId(NEW_ID);
    }

}
