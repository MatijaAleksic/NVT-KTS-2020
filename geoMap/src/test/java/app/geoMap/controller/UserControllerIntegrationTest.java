package app.geoMap.controller;

import app.geoMap.dto.UserDTO;
import app.geoMap.dto.UserLoginDTO;
import app.geoMap.dto.UserTokenStateDTO;
import app.geoMap.model.User;
import app.geoMap.service.CustomUserDetailsService;
import app.geoMap.service.UserService;

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

import static app.geoMap.constants.UserConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerIntegrationTest {
	
	@Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;
    
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
    public void AtestGetUsers() {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
        
        ResponseEntity<UserDTO[]> responseEntity =
                restTemplate.exchange("/api/users",HttpMethod.GET, httpEntity, UserDTO[].class);

        UserDTO[] users = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, users.length);
        assertEquals(DB_USER_EMAIL, users[0].getEmail());
    }
	
    @Test
    public void BtestGetAllUsersPageable() {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
        
        ResponseEntity<UserDTO[]> responseEntity =
                restTemplate.exchange("/api/users/by-page?page=0&size=2",HttpMethod.GET, httpEntity, UserDTO[].class);

        UserDTO[] users = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, users.length);
        assertEquals(DB_USER_EMAIL, users[0].getEmail());
    }
    

    @Test
    public void CtestGetUser() {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
        
        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/api/users/1",HttpMethod.GET, httpEntity, UserDTO.class);

        UserDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(user);
        assertEquals(DB_USER_EMAIL, user.getEmail());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void DtestCreateUser() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new UserDTO(null,NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL),headers);
        
        int size = userService.findAll().size();
        
        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/api/users",HttpMethod.POST, httpEntity, UserDTO.class);

        UserDTO user = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(user);
        assertEquals(NEW_USER_EMAIL, user.getEmail());

        List<User> users = userService.findAll();
        assertEquals(size + 1, users.size());
        assertEquals(NEW_USER_EMAIL, users.get(users.size()-1).getEmail());

        userService.delete(user.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void EtestUpdateUser() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(new UserDTO(null,NEW_NAME, NEW_LAST_NAME, NEW_USER_NAME, NEW_PASSWORD, NEW_USER_EMAIL),headers);
        
        
        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/api/users/2",HttpMethod.PUT, httpEntity, UserDTO.class);

        UserDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(user);
        assertEquals(DB_USER_IDD, user.getId());
        assertEquals(NEW_USER_EMAIL, user.getEmail());

        User dbUser = userService.findOne(DB_USER_IDD);
        assertEquals(DB_USER_IDD, user.getId());
        assertEquals(NEW_USER_EMAIL, user.getEmail());

        userService.update(dbUser, dbUser.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void FtestDeleteUser() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
        
        int size = userService.findAll().size();

        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/users/2",HttpMethod.DELETE, httpEntity, Void.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(size - 1, userService.findAll().size());
    }
}