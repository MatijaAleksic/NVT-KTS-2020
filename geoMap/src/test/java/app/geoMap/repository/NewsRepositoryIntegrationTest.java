package app.geoMap.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import app.geoMap.model.News;

import static app.geoMap.constants.NewsConstants.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class NewsRepositoryIntegrationTest {
	
	@Autowired
	private NewsRepository newsRepository;
	
	@Test
	public void testFindByTitle() {
		News found = newsRepository.findByTitle(DB_NEWS_TITLE);
		assertEquals(DB_NEWS_TITLE, found.getTitle());
		
	}

}
