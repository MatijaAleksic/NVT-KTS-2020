package app.geoMap.service;


import app.geoMap.model.Rating;
import app.geoMap.repository.RatingRepository;
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

import static app.geoMap.constants.RatingConstants.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RatingServiceUnitTest {
	
	@Autowired
    private RatingService commentService;

    @MockBean
    private RatingRepository ratingRepository;

    @Before
    public void setup() {
        List<Rating> ratings =  new ArrayList<>();
        
        Rating newRating = new Rating(NEW_RATING_VALUE);
        Rating dbRating = new Rating(DB_RATING_VALUE);
        dbRating.setId(DB_RATING_ID);
        //dbRating.setUser(new User());
        ratings.add(dbRating);

        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Rating> ratingPage = new PageImpl<>(ratings,pageable,PAGEABLE_TOTAL_ELEMENTS);

        given(ratingRepository.findAll()).willReturn(ratings);
        given(ratingRepository.findAll(pageable)).willReturn(ratingPage);

        
        given(ratingRepository.findById(DB_RATING_ID)).willReturn(java.util.Optional.of(dbRating));

        given(ratingRepository.save(org.mockito.ArgumentMatchers.any())).willReturn(newRating);

        doNothing().when(ratingRepository).delete(dbRating);
    }

    @Test
    public void testFindAll() {
        List<Rating> found = commentService.findAll();

        verify(ratingRepository, times(1)).findAll();
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
    }

    @Test
    public void testFindAllPageable() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Rating> found = commentService.findAll(pageable);

        verify(ratingRepository, times(1)).findAll(pageable);
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
    }

    @Test
    public void testFindById() {
        Rating found = commentService.findOne(DB_USER_ID);

        verify(ratingRepository, times(1)).findById(DB_USER_ID);
        assertEquals(DB_USER_ID, found.getId());
    }

	@Test
    public void testCreate() throws Exception {
        Rating rating = new Rating(NEW_RATING_VALUE);
        Rating created = commentService.create(rating);

        verify(ratingRepository, times(1)).save(rating);

        assertEquals(NEW_RATING_VALUE, created.getValue(), 0);
    }

    @Test(expected = java.lang.AssertionError.class)
    public void testCreate_GivenNameAlreadyExists() throws Exception {
        Rating rating = new Rating(DB_RATING_VALUE);
        Rating created = commentService.create(rating);

        verify(ratingRepository, times(1)).findById(DB_RATING_ID);

        assertEquals(null, created);
    }

	@Test
    public void testUpdate() throws Exception {
        Rating rating = new Rating(NEW_RATING_VALUE);
        Rating created = commentService.update(rating,DB_USER_ID);

        verify(ratingRepository, times(1)).findById(DB_USER_ID);

        assertEquals(NEW_RATING_VALUE, created.getValue(), 0);
    }

    @Test
    @Rollback(true)
    public void testDelete() throws Exception {
        commentService.delete(DB_USER_ID);

        Rating savedRating = new Rating(NEW_RATING_VALUE);
        savedRating.setId(DB_USER_ID);

        verify(ratingRepository, times(1)).findById(DB_USER_ID);
    }
}