package app.geoMap.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.geoMap.model.Comment;
import app.geoMap.repository.CommentRepository;

@Service
public class CommentService implements ServiceInterface<Comment>{

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserService userService;
	

	public List<Comment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Comment findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Comment create(Comment entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Comment update(Comment entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
