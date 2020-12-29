package app.geoMap.controller;

import app.geoMap.dto.CommentDTO;
import app.geoMap.model.Comment;
import app.geoMap.service.CommentService;
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

import static app.geoMap.constants.CommentConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@Sql("classpath:/data-h2.sql")
public class CommentControllerIntegrationTest {
	@Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CommentService commentService;

    //KAD SKONTAS COMMENT TEK OVO MOZES URADITI
//    @Test
//    public void testGetAllCulturalContentCategories() {
//
//        ResponseEntity<CommentDTO[]> responseEntity =
//                restTemplate.getForEntity("/api/users",
//                        CommentDTO[].class);
//
//        CommentDTO[] users = responseEntity.getBody();
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, users.length);
//        assertEquals(DB_USER_EMAIL, users[0].getEmail());
//    }
//
//    @Test
//    public void testGetAllCulturalContentCategoriesPageable() {
//        ResponseEntity<CommentDTO[]> responseEntity =
//                restTemplate.getForEntity("/api/users/by-page?page=0&size=2",
//                        CommentDTO[].class);
//
//        CommentDTO[] users = responseEntity.getBody();
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, users.length);
//        assertEquals(DB_USER_EMAIL, users[0].getEmail());
//    }
//
//    @Test
//    public void testGetCulturalContentCategory() {
//        ResponseEntity<CommentDTO> responseEntity =
//                restTemplate.getForEntity("/api/users/1", CommentDTO.class);
//
//        CommentDTO user = responseEntity.getBody();
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(user);
//        assertEquals(DB_USER_EMAIL, user.getEmail());
//    }
//
//    @Test
//    @Transactional
//    @Rollback(true)
//    public void testCreateCulturalContentCategory() throws Exception {
//        int size = commentService.findAll().size(); // broj slogova pre ubacivanja novog
//
//        ResponseEntity<CommentDTO> responseEntity =
//                restTemplate.postForEntity("/api/users",
//                        new CommentDTO(null, NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL),
//                        CommentDTO.class);
//
//        // provera odgovora servera
//        CommentDTO user = responseEntity.getBody();
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        assertNotNull(user);
//        assertEquals(NEW_USER_EMAIL, user.getEmail());
//
//        List<Comment> users = commentService.findAll();
//        assertEquals(size + 1, users.size()); // mora biti jedan vise slog sada nego pre
//        // poslednja kategorija u listi treba da bude nova kategorija koja je ubacena u testu
//        assertEquals(NEW_USER_EMAIL, users.get(users.size()-1).getEmail());
//
//        // uklanjamo dodatu kategoriju
//        commentService.delete(user.getId());
//    }
//
//    @Test
//    @Transactional
//    @Rollback(true)
//    public void testUpdateCulturalContentCategory() throws Exception {
//        ResponseEntity<CommentDTO> responseEntity =
//                restTemplate.exchange("/api/cultural-content-category/1", HttpMethod.PUT,
//                        new HttpEntity<CommentDTO>(new CommentDTO(DB_USER_ID, NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL)),
//                        CommentDTO.class);
//
//        CommentDTO user = responseEntity.getBody();
//
//        // provera odgovora servera
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(user);
//        assertEquals(DB_USER_ID, user.getId());
//        assertEquals(NEW_USER_EMAIL, user.getEmail());
//
//        // provera da li je izmenjen slog u bazi
//        Comment dbUser = commentService.findOne(DB_USER_ID);
//        assertEquals(DB_USER_ID, user.getId());
//        assertEquals(NEW_USER_EMAIL, user.getEmail());
//
//        // vracanje podatka na staru vrednost
//        dbUser.setFirstName(NEW_NAME);
//        dbUser.setLastName(NEW_LAST_NAME);
//        dbUser.setUserName(NEW_USER_NAME);
//        dbUser.setPassword(NEW_PASSWORD);
//        dbUser.setEmail(NEW_USER_EMAIL);
//        commentService.update(dbUser, dbUser.getId());
//    }
//
//    @Test
//    @Transactional
//    @Rollback(true)
//    public void testDeleteCulturalContentCategory() throws Exception {
//        // ubacimo kategoriju koju cemo brisati
//        Comment user = commentService.create(new Comment(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL));
//        // preuzmemo trenutni broj kategorija
//        List<Comment> users = commentService.findAll();
//        int size = commentService.findAll().size();
//
//        // poziv REST servisa za brisanje
//        ResponseEntity<Void> responseEntity =
//                restTemplate.exchange("/api/users/" + user.getId(),
//                        HttpMethod.DELETE, new HttpEntity<Object>(null), Void.class);
//
//        // provera odgovora servera
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//        // mora biti jedan manje slog sada nego pre
//        assertEquals(size - 1, commentService.findAll().size());
//    }
}