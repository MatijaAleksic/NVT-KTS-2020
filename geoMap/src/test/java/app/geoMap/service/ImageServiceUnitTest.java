package app.geoMap.service;

import app.geoMap.model.Image;
import app.geoMap.repository.ImageRepository;
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

import static app.geoMap.constants.ImageConstants.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class ImageServiceUnitTest {
	@Autowired
    private ImageService imageService;

    @MockBean
    private ImageRepository imageRepository;


    @Before
    public void setup() {
        List<Image> users =  new ArrayList<>();
        users.add(new Image(NEW_IMAGE_NAME));

        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Image> userPage = new PageImpl<>(users,pageable,PAGEABLE_TOTAL_ELEMENTS);

        // Definisanje ponasanja test dvojnika imageRepository za findAll metodu
        given(imageRepository.findAll()).willReturn(users);

        given(imageRepository.findAll(pageable)).willReturn(userPage);

        Image user = new Image(NEW_IMAGE_NAME);
        Image savedUser = new Image(NEW_IMAGE_NAME);
        savedUser.setId(NEW_IMAGE_ID);

        given(imageRepository.findById(DB_IMAGE_ID)).willReturn(java.util.Optional.of(savedUser));

        given(imageRepository.findByName(NEW_IMAGE_NAME)).willReturn(null);

        Image userFound = new Image(DB_IMAGE_NAME);
        given(imageRepository.findByName(DB_IMAGE_NAME)).willReturn(userFound);

        given(imageRepository.findByName(NEW_IMAGE_NAME)).willReturn(null);
        given(imageRepository.save(user)).willReturn(savedUser);

        doNothing().when(imageRepository).delete(savedUser);
    }

    @Test
    public void testFindAll() {
        List<Image> found = imageService.findAll();

        verify(imageRepository, times(1)).findAll();
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
    }

    @Test
    public void testFindAllPageable() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Image> found = imageService.findAll(pageable);

        verify(imageRepository, times(1)).findAll(pageable);
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
    }

    @Test
    public void testFindById() {
        Image found = imageService.findOne(DB_IMAGE_ID);

        verify(imageRepository, times(1)).findById(DB_IMAGE_ID);
        assertEquals(DB_IMAGE_ID, found.getId());
    }

    @Test
    public void testCreate() throws Exception {
        Image user = new Image(NEW_IMAGE_NAME);
        Image created = imageService.create(user);

        verify(imageRepository, times(1)).findByName(NEW_IMAGE_NAME);
        verify(imageRepository, times(1)).save(user);

        assertEquals(NEW_IMAGE_NAME, created.getName());
    }

    @Test
    public void testCreate_GivenNameAlreadyExists() throws Exception {
        Image user = new Image(NEW_IMAGE_NAME);
        Image created = imageService.create(user);

        verify(imageRepository, times(1)).findByName(DB_IMAGE_NAME);

        assertEquals(null, created);
    }

    @Test
    public void testUpdate() throws Exception {
        Image user = new Image(NEW_IMAGE_NAME);
        Image created = imageService.update(user,DB_IMAGE_ID);

        verify(imageRepository, times(1)).findById(DB_IMAGE_ID);

        assertEquals(NEW_IMAGE_NAME, created.getName());
    }

    @Test
    @Rollback(true)
    public void testDelete() throws Exception {
        imageService.delete(DB_IMAGE_ID);

        Image savedUser = new Image(NEW_IMAGE_NAME);
        savedUser.setId(DB_IMAGE_ID);

        verify(imageRepository, times(1)).findById(DB_IMAGE_ID);
    }
}
