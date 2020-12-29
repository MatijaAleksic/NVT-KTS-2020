package app.geoMap.repository;

import app.geoMap.model.Image;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static app.geoMap.constants.ImageConstants.NEW_IMAGE_NAME;
import static org.junit.Assert.assertEquals;

import org.junit.Before;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ImageRepositoryUnitTest {
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ImageRepository imageRepository;


    @Before
    public void setUp() {
        entityManager.persist(new Image("imagenew"));
    }

    @Test
    public void testFindByName() {
        Image found = imageRepository.findByName(NEW_IMAGE_NAME);
        assertEquals(NEW_IMAGE_NAME, found.getName());
    }
}