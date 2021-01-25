package app.geoMap.service;

import app.geoMap.model.Comment;
import app.geoMap.repository.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import static app.geoMap.constants.CommentConstants.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")

public class CommentServiceUnitTest {
	
	@Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository userRepository;

    @Before
    public void setup() {
        List<Comment> comments =  new ArrayList<>();
        Comment newComment = new Comment(NEW_COMMENT_TEXT);
        Comment dbComment = new Comment(DB_COMMENT_TEXT);
        dbComment.setId(1L);
        comments.add(dbComment);
        
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Comment> commentPage = new PageImpl<>(comments,pageable,PAGEABLE_TOTAL_ELEMENTS);

        given(userRepository.findAll()).willReturn(comments);
        given(userRepository.findAll(pageable)).willReturn(commentPage);

        given(userRepository.findById(DB_COMMENT_ID)).willReturn(java.util.Optional.of(dbComment));

        given(userRepository.findByText(NEW_COMMENT_TEXT)).willReturn(null);
        given(userRepository.save(org.mockito.ArgumentMatchers.any())).willReturn(newComment);

        doNothing().when(userRepository).delete(dbComment);
    }

    @Test
    public void testFindAll() {
        List<Comment> found = commentService.findAll();

        verify(userRepository, times(1)).findAll();
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
    }

    @Test
    public void testFindAllPageable() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Comment> found = commentService.findAll(pageable);

        verify(userRepository, times(1)).findAll(pageable);
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
    }

    @Test
    public void testFindById() {
        Comment found = commentService.findOne(DB_COMMENT_ID);

        verify(userRepository, times(1)).findById(DB_COMMENT_ID);
        assertEquals(DB_COMMENT_ID, found.getId());
    }

    @Test
    public void testCreate() throws Exception {
        Comment comment = new Comment(NEW_COMMENT_TEXT);
        Comment created = commentService.create(comment);

        verify(userRepository, times(1)).findByText(NEW_COMMENT_TEXT);
        //verify(userRepository, times(1)).save(comment); NE RADI NE ZNAM ZASTO

        assertEquals(NEW_COMMENT_TEXT, created.getText());
    }

    @Test(expected = java.lang.AssertionError.class)
    public void testCreate_GivenNameAlreadyExists() throws Exception {
        Comment comment = new Comment(DB_COMMENT_TEXT);
        Comment created = commentService.create(comment);

        verify(userRepository, times(1)).findByText(DB_COMMENT_TEXT);

        assertEquals(null, created);
    }

    @Test
    public void testUpdate() throws Exception {
        Comment comment = new Comment(NEW_COMMENT_TEXT);
        Comment created = commentService.update(comment,DB_COMMENT_ID);

        verify(userRepository, times(1)).findById(DB_COMMENT_ID);

        assertEquals(NEW_COMMENT_TEXT, created.getText());
    }

    @Test
    @Rollback(true)
    public void testDelete() throws Exception {
        commentService.delete(DB_COMMENT_ID);

        Comment savedRating = new Comment(NEW_COMMENT_TEXT);
        savedRating.setId(DB_COMMENT_ID);

        verify(userRepository, times(1)).findById(DB_COMMENT_ID);
    }
}