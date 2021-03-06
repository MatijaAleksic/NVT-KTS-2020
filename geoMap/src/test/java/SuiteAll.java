import app.geoMap.repository.UserRepositoryIntegrationTest;
import app.geoMap.repository.UserRepositoryUnitTest;
import app.geoMap.repository.RatingRepositoryIntegrationTest;
import app.geoMap.repository.RatingRepositoryUnitTest;
import app.geoMap.controller.CommentControllerIntegrationTest;
import app.geoMap.controller.ImageControllerIntegrationTest;
import app.geoMap.controller.RatingControllerIntegrationTest;
import app.geoMap.controller.UserControllerIntegrationTest;
import app.geoMap.repository.CommentRepositoryIntegrationTest;
import app.geoMap.repository.CommentRepositoryUnitTest;
import app.geoMap.repository.ImageRepositoryIntegrationTest;
import app.geoMap.repository.ImageRepositoryUnitTest;


import app.geoMap.repository.CulturalOfferRepositoryIntegrationTest;
import app.geoMap.repository.CulturalOfferRepositoryUnitTest;

import app.geoMap.repository.CultureSubtypeRepositoryIntegrationTest;
import app.geoMap.repository.CultureSybtypeRepositoryUnitTest;

import app.geoMap.repository.CultureTypeRepositoryIntegraionTest;
import app.geoMap.repository.CultureTypeRepositoryUnitTest;

import app.geoMap.repository.NewsRepositoryIntegrationTest;
import app.geoMap.repository.NewsRepositoryUnitTest;

import app.geoMap.service.UserServiceIntegrationTest;
import app.geoMap.service.UserServiceUnitTest;
import app.geoMap.service.RatingServiceIntegrationTest;
import app.geoMap.service.RatingServiceUnitTest;
import app.geoMap.service.CommentServiceIntegrationTest;
import app.geoMap.service.CommentServiceUnitTest;
import app.geoMap.service.ImageServiceIntegrationTest;
import app.geoMap.service.ImageServiceUnitTest;
import app.geoMap.service.CulturalOfferServiceIntegrationTest;
import app.geoMap.service.CulturalOfferServiceUnitTest;
import app.geoMap.service.CultureSubtypeServiceIntegrationTest;
import app.geoMap.service.CultureSubtypeServiceUnitTest;
import app.geoMap.service.CultureTypeServiceIntegrationTest;
import app.geoMap.service.CultureTypeServiceUnitTest;
import app.geoMap.service.NewsServiceIntegrationTest;
import app.geoMap.service.NewsServiceUnitTest;

import app.geoMap.controller.CulturalOfferControllerIntegrationTest;
import app.geoMap.controller.CultureSubtypeControllerIntegrationTest;
import app.geoMap.controller.CultureTypeControllerIntegrationTest;
import app.geoMap.controller.NewsControllerIntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@SuiteClasses({UserServiceIntegrationTest.class, RatingServiceIntegrationTest.class,ImageServiceIntegrationTest.class,
	CommentServiceIntegrationTest.class, UserRepositoryIntegrationTest.class, RatingRepositoryIntegrationTest.class,
	UserServiceUnitTest.class,CommentServiceUnitTest.class, RatingServiceUnitTest.class, UserRepositoryUnitTest.class,
	ImageRepositoryUnitTest.class,CommentRepositoryUnitTest.class, RatingRepositoryUnitTest.class,ImageRepositoryIntegrationTest.class,
	CommentRepositoryIntegrationTest.class, ImageServiceUnitTest.class, UserControllerIntegrationTest.class,
	RatingControllerIntegrationTest.class, CommentControllerIntegrationTest.class, ImageControllerIntegrationTest.class,
	CulturalOfferServiceIntegrationTest.class, CulturalOfferServiceUnitTest.class, CultureSubtypeServiceIntegrationTest.class,
	CultureSubtypeServiceUnitTest.class, CultureTypeServiceIntegrationTest.class, CultureTypeServiceUnitTest.class,
	NewsServiceIntegrationTest.class, NewsServiceUnitTest.class, CulturalOfferRepositoryIntegrationTest.class,
	CultureSubtypeRepositoryIntegrationTest.class, CultureSybtypeRepositoryUnitTest.class,
	CultureTypeRepositoryIntegraionTest.class, CultureTypeRepositoryUnitTest.class, NewsRepositoryIntegrationTest.class,
	NewsRepositoryUnitTest.class, CulturalOfferControllerIntegrationTest.class, CultureSubtypeControllerIntegrationTest.class,
	CultureTypeControllerIntegrationTest.class, NewsControllerIntegrationTest.class })
@TestPropertySource("classpath:test.properties")
public class SuiteAll {
	


}
