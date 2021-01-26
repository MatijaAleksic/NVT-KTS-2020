package app.geoMap.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import app.geoMap.dto.NewsDTO;
import app.geoMap.dto.UserLoginDTO;
import app.geoMap.dto.UserTokenStateDTO;
import app.geoMap.model.News;
import app.geoMap.service.NewsService;

import static app.geoMap.constants.NewsConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private NewsService newsService;
	
	private String accessToken;
	
	@Before
	public void login() {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in", 
				new UserLoginDTO("markoMarkovic@maildrop.cc", "MarkoMarkovic12"), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}
	

	@Test
	@Order(1)
	public void testGetAllNews() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		
		
		ResponseEntity<NewsDTO[]>  responseEntity =
				restTemplate.exchange("/api/news", HttpMethod.GET, httpEntity, NewsDTO[].class);
		
		NewsDTO[] news = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, news.length);
		assertEquals(DB_NEWS_TITLE, news[0].getTitle());
		
	}
	
	@Test
	@Order(2)
	public void testGetNews() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		
		ResponseEntity<NewsDTO>  responseEntity =
				restTemplate.exchange("/api/news/1", HttpMethod.GET, httpEntity, NewsDTO.class);
		
		NewsDTO news = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(news);
		assertEquals(DB_NEWS_TITLE, news.getTitle());
			
	}
	
	@Test
	@Order(3)
	@Transactional
    @Rollback(true)
	public void testCreateNews() throws Exception {
		int size = newsService.findAll().size();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<NewsDTO> httpEntity = new HttpEntity<>(new NewsDTO(null, NEWS_TITLE, NEWS_DATE),headers);
		
		ResponseEntity<NewsDTO>  responseEntity =
				restTemplate.exchange("/api/news", HttpMethod.POST, httpEntity, NewsDTO.class);
		
		NewsDTO news = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(news);
		assertEquals(DB_NEWS_TITLE, news.getTitle());
		
		List<News> allNews = newsService.findAll();
		assertEquals(size + 1, allNews.size());
		assertEquals(NEWS_TITLE, allNews.get(allNews.size()-1).getTitle());

		
	}
	
	@Test
	@Order(4)
	@Transactional
    @Rollback(true)
	public void testUpdateNews() throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<NewsDTO> httpEntity = new HttpEntity<>(new NewsDTO(DB_NEWS_ID, NEWS_TITLE, NEWS_DATE),headers);

		ResponseEntity<NewsDTO>  responseEntity =
				restTemplate.exchange("/api/news/1", HttpMethod.PUT, httpEntity, NewsDTO.class);
		
		NewsDTO news = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(news);
		assertEquals(DB_NEWS_ID, news.getId());
		assertEquals(NEWS_TITLE, news.getTitle());
		
		News dbNews = newsService.findOne(DB_NEWS_ID);
		assertEquals(DB_NEWS_ID, news.getId());
		assertEquals(NEWS_TITLE, news.getTitle());
		
		dbNews.setTitle(NEWS_TITLE);
		dbNews.setCreationDate(NEWS_DATE);
		
		newsService.update(dbNews, dbNews.getId());
		
	}
	
	@Test
	@Order(5)
	@Transactional
    @Rollback(true)
	public void testDeleteNews() throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<NewsDTO> httpEntity = new HttpEntity<>(headers);
		
		//News news = newsService.create(new News( NEWS_TITLE, NEWS_DATE));
		
		List<News> allNews = newsService.findAll();
		int size = newsService.findAll().size();
		
		ResponseEntity<Void> responseEntity =
				restTemplate.exchange("/api/news/1" ,
				HttpMethod.DELETE, httpEntity, Void.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(size -1, newsService.findAll().size());
		
	}
	

}
