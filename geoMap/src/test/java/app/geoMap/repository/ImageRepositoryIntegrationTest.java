package app.geoMap.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static app.geoMap.constants.ImageConstants.DB_IMAGE_NAME;
import static org.junit.Assert.assertEquals;

import app.geoMap.model.Image;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ImageRepositoryIntegrationTest {

	@Autowired
    private ImageRepository imageRepository;

	@Sql("classpath:/data-h2.sql")
    @Test
    public void testFindByName() {
        Image found = imageRepository.findByName(DB_IMAGE_NAME);
        assertEquals(DB_IMAGE_NAME, found.getName());
    }
}
