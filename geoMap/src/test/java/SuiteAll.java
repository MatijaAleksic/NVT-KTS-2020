import app.geoMap.repository.UserRepositoryIntegrationTest;
import app.geoMap.repository.UserRepositoryUnitTest;
import app.geoMap.repository.RatingRepositoryIntegrationTest;
import app.geoMap.repository.RatingRepositoryUnitTest;
import app.geoMap.repository.CommentRepositoryIntegrationTest;
import app.geoMap.repository.CommentRepositoryUnitTest;
import app.geoMap.repository.ImageRepositoryIntegrationTest;
import app.geoMap.repository.ImageRepositoryUnitTest;
import app.geoMap.service.UserServiceIntegrationTest;
import app.geoMap.service.UserServiceUnitTest;
import app.geoMap.service.RatingServiceIntegrationTest;
import app.geoMap.service.RatingServiceUnitTest;
import app.geoMap.service.CommentServiceIntegrationTest;
import app.geoMap.service.CommentServiceUnitTest;
import app.geoMap.service.ImageServiceIntegrationTest;
import app.geoMap.service.ImageServiceUnitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@SuiteClasses({UserServiceIntegrationTest.class, RatingServiceIntegrationTest.class,ImageServiceIntegrationTest.class,
	CommentServiceIntegrationTest.class, UserRepositoryIntegrationTest.class, RatingRepositoryIntegrationTest.class,
	ImageRepositoryIntegrationTest.class,CommentRepositoryIntegrationTest.class, ImageServiceUnitTest.class, UserServiceUnitTest.class,
	CommentServiceUnitTest.class, RatingServiceUnitTest.class, UserRepositoryUnitTest.class, ImageRepositoryUnitTest.class,
	CommentRepositoryUnitTest.class, RatingRepositoryUnitTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteAll {
	


}
