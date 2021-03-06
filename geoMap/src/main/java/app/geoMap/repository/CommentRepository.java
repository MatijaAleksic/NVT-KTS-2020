package app.geoMap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.geoMap.model.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	Comment findByText(String text);
	
	Comment findByUser_Email(String email);
	
	Comment findByUser_Id(Long id);

}
