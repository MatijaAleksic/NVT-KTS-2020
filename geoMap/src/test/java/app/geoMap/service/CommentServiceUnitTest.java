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

    //MORAS SKONTATI KAKO DA NADJES RATING PREKO USERA
//    @Before
//    public void setup() {
//        List<Comment> users =  new ArrayList<>();
//        users.add(new Comment(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL));
//
//        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
//        Page<Comment> commentPage = new PageImpl<>(users,pageable,PAGEABLE_TOTAL_ELEMENTS);
//
//        // Definisanje ponasanja test dvojnika userRepository za findAll metodu
//        given(userRepository.findAll()).willReturn(users);
//
//        given(userRepository.findAll(pageable)).willReturn(commentPage);
//
//        Comment rating = new Comment(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
//        Comment savedRating = new Comment(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
//        savedRating.setId(NEW_ID);
//
//        given(userRepository.findById(DB_COMMENT_ID)).willReturn(java.util.Optional.of(savedRating));
//
//        given(userRepository.findByUserName(NEW_USER_NAME)).willReturn(null);
//
//        Comment userFound = new Comment(DB_USER_NAME, DB_USER_EMAIL);
//        given(userRepository.findByEmail(DB_USER_EMAIL)).willReturn(userFound);
//
//        given(userRepository.findByUserName(NEW_USER_NAME)).willReturn(null);
//        given(userRepository.save(rating)).willReturn(savedRating);
//
//        doNothing().when(userRepository).delete(savedRating);
//    }
//
//    @Test
//    public void testFindAll() {
//        List<Comment> found = commentService.findAll();
//
//        verify(userRepository, times(1)).findAll();
//        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
//    }
//
//    @Test
//    public void testFindAllPageable() {
//        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
//        Page<Comment> found = commentService.findAll(pageable);
//
//        verify(userRepository, times(1)).findAll(pageable);
//        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
//    }
//
//    @Test
//    public void testFindById() {
//        Comment found = commentService.findOne(DB_COMMENT_ID);
//
//        verify(userRepository, times(1)).findById(DB_COMMENT_ID);
//        assertEquals(DB_COMMENT_ID, found.getId());
//    }
//
//    @Test
//    public void testCreate() throws Exception {
//        Comment rating = new Comment(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
//        Comment created = commentService.create(rating);
//
//        verify(userRepository, times(1)).findByEmail(NEW_USER_EMAIL);
//        verify(userRepository, times(1)).save(rating);
//
//        assertEquals(NEW_USER_NAME, created.getUsername());
//    }
//
//    @Test
//    public void testCreate_GivenNameAlreadyExists() throws Exception {
//        Comment rating = new Comment(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
//        Comment created = commentService.create(rating);
//
//        verify(userRepository, times(1)).findByEmail(DB_USER_EMAIL);
//
//        assertEquals(null, created);
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//        Comment rating = new Comment(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
//        Comment created = commentService.update(rating,DB_COMMENT_ID);
//
//        verify(userRepository, times(1)).findById(DB_COMMENT_ID);
//
//        assertEquals(NEW_USER_NAME, created.getUsername());
//    }
//
//    @Test
//    @Rollback(true)
//    public void testDelete() throws Exception {
//        commentService.delete(DB_COMMENT_ID);
//
//        Comment savedRating = new Comment(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
//        savedRating.setId(DB_COMMENT_ID);
//
//        verify(userRepository, times(1)).findById(DB_COMMENT_ID);
//    }
}