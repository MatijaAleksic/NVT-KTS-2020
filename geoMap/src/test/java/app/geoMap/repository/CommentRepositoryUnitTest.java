package app.geoMap.repository;

import static app.geoMap.constants.CommentConstants.DB_NEW_USER_ID;
import static app.geoMap.constants.CommentConstants.DB_NEW_COMMENT_TEXT;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import app.geoMap.model.Comment;
import app.geoMap.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryUnitTest {

	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;


    @Before
    public void setUp() {
    	User user = new User("matija", "aleksic", "matija123", "aleksic123", "matijaAleksic@maildrop.cc");
        entityManager.persist(new Comment("Neki tekst", user));
    }

    @Test
    public void testfindByUser_Email() {
        Comment found = commentRepository.findByUser_Email("matijaAleksic@maildrop.cc");
        assertEquals("matijaAleksic@maildrop.cc", found.getUser().getEmail());
    }
    
    @Test
    public void testfindByUser_Id() {
    	Comment found = commentRepository.findByUser_Id(DB_NEW_USER_ID);
        assertEquals(DB_NEW_USER_ID, found.getUser().getId());
    }
    
    @Test
    public void testfindByText() {
    	Comment found = commentRepository.findByText(DB_NEW_COMMENT_TEXT);
        assertEquals(DB_NEW_COMMENT_TEXT, found.getText());
    }
}
