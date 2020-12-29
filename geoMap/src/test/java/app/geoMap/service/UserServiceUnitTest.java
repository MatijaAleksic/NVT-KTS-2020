package app.geoMap.service;


import app.geoMap.model.User;
import app.geoMap.repository.UserRepository;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import static app.geoMap.constants.UserConstants.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceUnitTest {
	
	@Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    @Before
    public void setup() {
        List<User> users =  new ArrayList<>();
        users.add(new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL));

        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<User> userPage = new PageImpl<>(users,pageable,PAGEABLE_TOTAL_ELEMENTS);

        // Definisanje ponasanja test dvojnika userRepository za findAll metodu
        given(userRepository.findAll()).willReturn(users);

        given(userRepository.findAll(pageable)).willReturn(userPage);

        User user = new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
        User savedUser = new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
        savedUser.setId(NEW_ID);

        given(userRepository.findById(DB_USER_ID)).willReturn(java.util.Optional.of(savedUser));

        given(userRepository.findByUserName(NEW_USER_NAME)).willReturn(null);

        User userFound = new User(DB_USER_NAME, DB_USER_EMAIL);
        given(userRepository.findByEmail(DB_USER_EMAIL)).willReturn(userFound);

        given(userRepository.findByUserName(NEW_USER_NAME)).willReturn(null);
        given(userRepository.save(user)).willReturn(savedUser);

        doNothing().when(userRepository).delete(savedUser);
    }
    
    @Test
    @Sql("classpath:/data-h2.sql")
    public void testFindAllPageable() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<User> found = userService.findAll(pageable);

        //verify(userRepository, times(1)).findAll(pageable);
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
    }

    @Test
    public void testFindAll() {
        List<User> found = userService.findAll();

        verify(userRepository, times(1)).findAll();
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
    }

    @Test
    public void testFindById() {
        User found = userService.findOne(DB_USER_ID);

        verify(userRepository, times(1)).findById(DB_USER_ID);
        assertEquals(DB_USER_ID, found.getId());
    }

    @Test
    public void testCreate() throws Exception {
        User user = new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
        User created = userService.create(user);

        verify(userRepository, times(1)).findByEmail(NEW_USER_EMAIL);
        verify(userRepository, times(1)).save(user);

        assertEquals(NEW_USER_NAME, created.getUsername());
    }

    @Test
    public void testCreate_GivenNameAlreadyExists() throws Exception {
        User user = new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
        User created = userService.create(user);

        verify(userRepository, times(1)).findByEmail(DB_USER_EMAIL);

        assertEquals(null, created);
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
        User created = userService.update(user,DB_USER_ID);

        verify(userRepository, times(1)).findById(DB_USER_ID);

        assertEquals(NEW_USER_NAME, created.getUsername());
    }

    @Test
    @Rollback(true)
    public void testDelete() throws Exception {
        userService.delete(DB_USER_ID);

        User savedUser = new User(NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL);
        savedUser.setId(DB_USER_ID);

        verify(userRepository, times(1)).findById(DB_USER_ID);
    }
}