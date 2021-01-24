package app.geoMap.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import app.geoMap.repository.NewsRepository;
import app.geoMap.model.News;

import static app.geoMap.constants.NewsConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsServiceUnitTest {
	
	@Autowired
	private NewsService newsService;
	
	@MockBean
	private NewsRepository newsRepository;
	
	@Before
	public void setUp() {
		
		List<News> news = new ArrayList<>();
		News newNews = new News(NEWS_TITLE, NEWS_DATE);
		News dbNews = new News(DB_NEWS_TITLE, DB_NEWS_DATE);
		dbNews.setId(1L);
		
		//news.add(newNews);
		news.add(dbNews);
		given(newsRepository.findAll()).willReturn(news);
		
		given(newsRepository.findById(DB_NEWS_ID)).willReturn(java.util.Optional.of(dbNews));
		given(newsRepository.findByTitle(NEWS_TITLE)).willReturn(null);
		
		given(newsRepository.findByTitle(DB_NEWS_TITLE)).willReturn(dbNews);
		
		given(newsRepository.save(org.mockito.ArgumentMatchers.any())).willReturn(newNews);
		
		doNothing().when(newsRepository).delete(newNews);
		
		
	}
	
	@Test
	public void testFindAll() {
		List<News> found = newsService.findAll();
		
		verify(newsRepository, times(1)).findAll();
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
		
	}
	
	@Test
	public void testFindOne() {
		News found = newsService.findOne(DB_NEWS_ID);
		
		verify(newsRepository, times(1)).findById(DB_NEWS_ID);
		assertEquals(DB_NEWS_ID, found.getId());
		
	}
	
	@Test
	public void testCreate() throws Exception {
		News news = new News(NEWS_TITLE, NEWS_DATE);
		News created = newsService.create(news);
		
		verify(newsRepository, times(1)).findByTitle(NEWS_TITLE);
		verify(newsRepository, times(1)).save(news);
		
		assertEquals(NEWS_TITLE, created.getTitle());
		
	}
	
	@Test
	public void testUpdate() throws Exception {
		News news = new News(NEWS_TITLE, NEWS_DATE);
		News created = newsService.update(news, DB_NEWS_ID);
		
		verify(newsRepository, times(1)).findById(DB_NEWS_ID);
		
		assertEquals(NEWS_TITLE, created.getTitle());
		
	}
	
	@Test
	@Transactional
	public void testDelete() throws Exception {
		newsService.delete(DB_NEWS_ID);
		
		News savedNews = new News(NEWS_TITLE, NEWS_DATE);
		savedNews.setId(DB_NEWS_ID);
		
		verify(newsRepository, times(1)).findById(DB_NEWS_ID);
	}
	
	

}
