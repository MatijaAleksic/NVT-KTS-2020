package app.geoMap.service;

import app.geoMap.model.Comment;
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

import static app.geoMap.constants.CommentConstants.*;
import static app.geoMap.constants.UserConstants.NEW_LAST_NAME;
import static app.geoMap.constants.UserConstants.NEW_NAME;
import static app.geoMap.constants.UserConstants.NEW_PASSWORD;
import static app.geoMap.constants.UserConstants.NEW_USER_EMAIL;
import static app.geoMap.constants.UserConstants.NEW_USER_NAME;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentServiceIntegrationTest {
	
	@Autowired
    private  CommentService commentService;

	@Sql("classpath:/data-h2.sql")
    @Test
    public void testFindAllPageable() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Comment> found = commentService.findAll(pageable);
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
    }
	
    @Test
    public void testFindAll() {
        List<Comment> found = commentService.findAll();
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
    }

    @Test
    public void testFindById() {
    	Comment found = commentService.findOne(DB_COMMENT_ID);
        assertEquals(DB_COMMENT_ID, found.getId());
    }

    @Test
    @Order(1)
    public void testCreate() throws Exception {
    	Comment comment = new Comment(DB_NEW_COMMENT_TEXT, new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL));  
    	comment.setId(DB_NEW_COMMENT_ID);
    	Comment created = commentService.create(comment);

        assertEquals(NEW_USER_EMAIL, created.getUser().getEmail());
    }

    @Test
    @Order(2)
    public void testUpdate() throws Exception {
        Comment comment = new Comment(DB_NEW_COMMENT_TEXT, new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL)); 
        Comment created = commentService.update(comment,DB_NEW_COMMENT_IDD);

        assertEquals(NEW_USER_EMAIL, created.getUser().getEmail());
    }

    @Test
    @Order(3)
    public void testDelete() throws Exception {
    	commentService.delete(DB_NEW_COMMENT_ID);

        Comment savedComment = new Comment(DB_NEW_COMMENT_TEXT, new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL));
        savedComment.setId(DB_NEW_COMMENT_ID);
    }
}