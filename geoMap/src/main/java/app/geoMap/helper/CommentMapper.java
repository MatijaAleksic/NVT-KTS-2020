package app.geoMap.helper;

import app.geoMap.dto.CommentDTO;
import app.geoMap.model.Comment;

public class CommentMapper implements MapperInterface<Comment, CommentDTO>{

	@Override
	public Comment toEntity(CommentDTO dto) {
		return new Comment(dto.getText());
	}

	@Override
	public CommentDTO toDto(Comment entity) {
        return new CommentDTO(entity.getId(), entity.getText());
	}
}