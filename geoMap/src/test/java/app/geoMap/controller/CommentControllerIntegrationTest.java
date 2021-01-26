package app.geoMap.controller;

import app.geoMap.dto.CommentDTO;
import app.geoMap.dto.UserLoginDTO;
import app.geoMap.dto.UserTokenStateDTO;
import app.geoMap.model.Comment;
import app.geoMap.service.CommentService;
import app.geoMap.service.CustomUserDetailsService;

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

import static app.geoMap.constants.CommentConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommentControllerIntegrationTest {
	@Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CommentService commentService;
    
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
    public void AtestGetAllComments() {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

        ResponseEntity<CommentDTO[]> responseEntity =
                restTemplate.exchange("/api/comments",HttpMethod.GET, httpEntity, CommentDTO[].class);

        
        CommentDTO[] comments = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, comments.length);
        assertEquals(DB_COMMENT_ID, comments[0].getId());
    }

    @Test
    public void BtestGetAllComments() {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
        
        ResponseEntity<CommentDTO[]> responseEntity =
                restTemplate.exchange("/api/comments/by-page?page=0&size=2\"",HttpMethod.GET, httpEntity, CommentDTO[].class);


        CommentDTO[] comments = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, comments.length);
        assertEquals(DB_COMMENT_ID, comments[0].getId());
    }

    @Test
    public void CtestGetComment() {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
        
        ResponseEntity<CommentDTO> responseEntity =
                restTemplate.exchange("/api/comments/1",HttpMethod.GET,httpEntity, CommentDTO.class);

        CommentDTO comment = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(comment);
        assertEquals(DB_COMMENT_ID, comment.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void DtestCreateComment() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new CommentDTO(null, NEW_COMMENT_TEXT), headers);
        
        int size = commentService.findAll().size(); // broj slogova pre ubacivanja novog
        
        ResponseEntity<CommentDTO> responseEntity =
                restTemplate.exchange("/api/comments",HttpMethod.POST,httpEntity, CommentDTO.class);


        // provera odgovora servera
        CommentDTO comment = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(comment);
        assertEquals(NEW_COMMENT_TEXT, comment.getText());

        List<Comment> comments = commentService.findAll();
        assertEquals(size + 1, comments.size()); // mora biti jedan vise slog sada nego pre
        // poslednja kategorija u listi treba da bude nova kategorija koja je ubacena u testu
        assertEquals(NEW_COMMENT_TEXT, comments.get(comments.size()-1).getText());

        // uklanjamo dodatu kategoriju
        commentService.delete(comment.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void EtestUpdateComment() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new CommentDTO(null, NEW_COMMENT_TEXT), headers);
            
        
        ResponseEntity<CommentDTO> responseEntity =
                restTemplate.exchange("/api/comments/1",HttpMethod.PUT,httpEntity, CommentDTO.class);


        CommentDTO comment = responseEntity.getBody();

        // provera odgovora servera
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(comment);
        assertEquals(DB_COMMENT_ID, comment.getId());
        assertEquals(NEW_COMMENT_TEXT, comment.getText());

        // provera da li je izmenjen slog u bazi
        Comment dbComment = commentService.findOne(DB_COMMENT_ID);
        assertEquals(DB_COMMENT_ID, dbComment.getId());
        assertEquals(NEW_COMMENT_TEXT, dbComment.getText());

        commentService.update(dbComment, dbComment.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void FtestDeleteComment() throws Exception {
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
        
        int size = commentService.findAll().size();
        
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/comments/1",HttpMethod.DELETE,httpEntity, Void.class);

        // provera odgovora servera
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // mora biti jedan manje slog sada nego pre
        assertEquals(size - 1, commentService.findAll().size());
    }
}