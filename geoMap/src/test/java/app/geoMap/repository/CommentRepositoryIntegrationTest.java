package app.geoMap.repository;

import static app.geoMap.constants.CommentConstants.DB_USER_EMAIL;
import static app.geoMap.constants.CommentConstants.DB_USER_ID;
import static app.geoMap.constants.CommentConstants.DB_COMMENT_TEXT;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import app.geoMap.model.Comment;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryIntegrationTest {
	
	@Autowired
    private CommentRepository commentRepository;

	@Sql("classpath:/data-h2.sql")
    @Test
    public void testfindByUser_Id() {
        Comment found = commentRepository.findByUser_Id(DB_USER_ID);
        assertEquals(DB_USER_ID, found.getUser().getId());
    }
	
	@Sql("classpath:/data-h2.sql")
    @Test
    public void testfindByUser_Email() {
        Comment found = commentRepository.findByUser_Email(DB_USER_EMAIL);
        assertEquals(DB_USER_EMAIL, found.getUser().getEmail());
    }
	
	@Sql("classpath:/data-h2.sql")
    @Test
    public void testfindByText() {
        Comment found = commentRepository.findByText(DB_COMMENT_TEXT);
        assertEquals(DB_COMMENT_TEXT, found.getText());
    }

}
