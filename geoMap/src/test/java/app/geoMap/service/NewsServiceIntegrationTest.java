package app.geoMap.service;

import static app.geoMap.constants.NewsConstants.*;
import static org.junit.Assert.assertEquals;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import app.geoMap.model.News;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsServiceIntegrationTest {
	
	@Autowired
	private NewsService newsService;
	
	@Test
	public void testFindAll() {
		List<News> found = newsService.findAll();
		
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
		
	}
	
	@Test
	public void testFindOne() {
		News found = newsService.findOne(DB_NEWS_ID);
		
		assertEquals(DB_NEWS_ID, found.getId());
		
	}
	
	@Test
	public void testCreate() throws Exception {
		News news = new News(NEWS_TITLE, NEWS_DATE);
		news.setId(NEWS_ID);
		News created = newsService.create(news);
		
		
		assertEquals(NEWS_TITLE, created.getTitle());
		
	}
	
	@Test
	@Transactional
	public void testUpdate() throws Exception {
		News news = new News(NEWS_TITLE, NEWS_DATE);
		News created = newsService.update(news, NEWS_ID);
		
		
		assertEquals(NEWS_TITLE, created.getTitle());
		
	}
	
	@Test
	@Transactional
	public void testDelete() throws Exception {
		newsService.delete(DB_NEWS_ID);
		
		News savedNews = new News(NEWS_TITLE, NEWS_DATE);
		savedNews.setId(DB_NEWS_ID);
		
	}
	

}
