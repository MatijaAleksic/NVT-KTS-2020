package app.geoMap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.geoMap.model.Comment;
import app.geoMap.repository.CommentRepository;

@Service
public class CommentService implements ServiceInterface<Comment>{

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}
	
	public Page<Comment> findAll(Pageable pageable) {
		return commentRepository.findAll(pageable);
	}
	
	@Override
	public Comment findOne(Long id) {
		return commentRepository.findById(id).orElse(null);
	}

	@Override
	public Comment create(Comment entity) throws Exception {
		if(commentRepository.findByText(entity.getText()) != null){
            throw new Exception("Comment with username already exists");
        }
		Comment c = new Comment();
		c.setText(entity.getText());
		c.setUser(entity.getUser());
		return commentRepository.save(c);
	}

	@Override
	public Comment update(Comment entity, Long id) throws Exception {
		Comment existingComment =  commentRepository.findById(id).orElse(null);
        if(existingComment == null){
            throw new Exception("Comment with given id doesn't exist");
        }
        existingComment.setText(entity.getText());
        existingComment.setUser(entity.getUser());
        return commentRepository.save(existingComment);
	}

	@Override
	public void delete(Long id) throws Exception {
		Comment existingComment = commentRepository.findById(id).orElse(null);
        if(existingComment == null){
            throw new Exception("Comment with given id doesn't exist");
        }
        commentRepository.delete(existingComment);
	}
}
