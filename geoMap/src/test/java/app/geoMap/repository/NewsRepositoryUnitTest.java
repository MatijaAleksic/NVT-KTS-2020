package app.geoMap.repository;

import static app.geoMap.constants.NewsConstants.NEWS_DATE;
import static app.geoMap.constants.NewsConstants.NEWS_TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import app.geoMap.model.News;

@RunWith(SpringRunner.class)
@DataJpaTest
//@TestPropertySource("classpath:test.properties")
public class NewsRepositoryUnitTest {
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired 
	private NewsRepository newsRepository;
	
	@Before
	public void setUp() {
		entityManager.persist(new News(NEWS_TITLE, NEWS_DATE));
	}
	
	@Test
	public void testFindByTitle() {
		News found = newsRepository.findByTitle(NEWS_TITLE);
		assertEquals(NEWS_TITLE, found.getTitle());
	}

}
