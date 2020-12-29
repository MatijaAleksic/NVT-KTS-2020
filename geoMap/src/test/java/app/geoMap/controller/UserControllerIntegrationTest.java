package app.geoMap.controller;

import app.geoMap.dto.UserDTO;
import app.geoMap.model.User;
import app.geoMap.service.UserService;
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

import static app.geoMap.constants.UserConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {
	@Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Sql("classpath:/data-h2.sql")
    @Test
    public void testGetAllUsersPageable() {
        ResponseEntity<UserDTO[]> responseEntity =
                restTemplate.getForEntity("/api/users/by-page?page=0&size=2",
                        UserDTO[].class);

        UserDTO[] users = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, users.length);
        assertEquals(DB_USER_EMAIL, users[0].getEmail());
    }
    
    
    @Test
    public void testGetUsers() {

//        login(DB_USER_EMAIL, DB_USER_PASSWORD);
//
//        //postavimo JWT token u zaglavlje zahteva da bi bilo dozvoljeno da pozovemo funkcionalnost
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", accessToken);
//        //kreiramo objekat koji saljemo u sklopu zahteva
//        // objekat nema telo, vec samo zaglavlje, jer je rec o GET zahtevu
//        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

        ResponseEntity<UserDTO[]> responseEntity =
                restTemplate.getForEntity("/api/users",
                        UserDTO[].class);

        UserDTO[] users = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, users.length);
        assertEquals(DB_USER_EMAIL, users[0].getEmail());
    }


    @Test
    public void testGetUser() {
        ResponseEntity<UserDTO> responseEntity =
                restTemplate.getForEntity("/api/users/1", UserDTO.class);

        UserDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(user);
        assertEquals(DB_USER_EMAIL, user.getEmail());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateUser() throws Exception {
        int size = userService.findAll().size(); // broj slogova pre ubacivanja novog

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.postForEntity("/api/users",
                        new UserDTO(null, NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL),
                        UserDTO.class);

        // provera odgovora servera
        UserDTO user = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(user);
        assertEquals(NEW_USER_EMAIL, user.getEmail());

        List<User> users = userService.findAll();
        assertEquals(size + 1, users.size()); // mora biti jedan vise slog sada nego pre
        // poslednja kategorija u listi treba da bude nova kategorija koja je ubacena u testu
        assertEquals(NEW_USER_EMAIL, users.get(users.size()-1).getEmail());

        // uklanjamo dodatu kategoriju
        userService.delete(user.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateUser() throws Exception {
        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/api/cultural-content-category/1", HttpMethod.PUT,
                        new HttpEntity<UserDTO>(new UserDTO(DB_USER_ID, NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL)),
                        UserDTO.class);

        UserDTO user = responseEntity.getBody();

        // provera odgovora servera
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(user);
        assertEquals(DB_USER_ID, user.getId());
        assertEquals(NEW_USER_EMAIL, user.getEmail());

        // provera da li je izmenjen slog u bazi
        User dbUser = userService.findOne(DB_USER_ID);
        assertEquals(DB_USER_ID, user.getId());
        assertEquals(NEW_USER_EMAIL, user.getEmail());

        // vracanje podatka na staru vrednost
        dbUser.setFirstName(NEW_NAME);
        dbUser.setLastName(NEW_LAST_NAME);
        dbUser.setUserName(NEW_USER_NAME);
        dbUser.setPassword(NEW_PASSWORD);
        dbUser.setEmail(NEW_USER_EMAIL);
        userService.update(dbUser, dbUser.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteUser() throws Exception {
        // ubacimo kategoriju koju cemo brisati
        User user = userService.create(new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL));
        // preuzmemo trenutni broj kategorija
        List<User> users = userService.findAll();
        int size = userService.findAll().size();

        // poziv REST servisa za brisanje
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/users/" + user.getId(),
                        HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);

        // provera odgovora servera
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // mora biti jedan manje slog sada nego pre
        assertEquals(size - 1, userService.findAll().size());
    }
}