import app.geoMap.repository.UserRepositoryIntegrationTest;
import app.geoMap.repository.UserRepositoryUnitTest;

import app.geoMap.repository.RatingRepositoryIntegrationTest;
import app.geoMap.repository.RatingRepositoryUnitTest;

import app.geoMap.repository.CommentRepositoryIntegrationTest;
import app.geoMap.repository.CommentRepositoryUnitTest;

import app.geoMap.repository.ImageRepositoryIntegrationTest;
import app.geoMap.repository.ImageRepositoryUnitTest;

import app.geoMap.service.UserServiceIntegrationTest;
import app.geoMap.service.RatingServiceIntegrationTest;
import app.geoMap.service.CommentServiceIntegrationTest;
import app.geoMap.service.ImageServiceIntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.jdbc.Sql;

@RunWith(Suite.class)
//@Sql("classpath:/data-h2.sql")
@SuiteClasses({UserRepositoryIntegrationTest.class, UserRepositoryUnitTest.class, RatingRepositoryIntegrationTest.class, 
	RatingRepositoryUnitTest.class, CommentRepositoryIntegrationTest.class, CommentRepositoryUnitTest.class, ImageRepositoryIntegrationTest.class,
	ImageRepositoryUnitTest.class,UserServiceIntegrationTest.class,RatingServiceIntegrationTest.class,CommentServiceIntegrationTest.class,	ImageServiceIntegrationTest.class, })
//@TestPropertySource("classpath:test.properties")
public class SuiteAll {
	


}
