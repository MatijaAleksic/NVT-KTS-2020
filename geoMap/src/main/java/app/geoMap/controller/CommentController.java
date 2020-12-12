package app.geoMap.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.geoMap.dto.CommentDTO;
import app.geoMap.helper.CommentMapper;
import app.geoMap.model.Comment;
import app.geoMap.service.CommentService;

@RestController
public class CommentController {
	@Autowired
    private CommentService commentService;

    private CommentMapper commentMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<Comment> comments = commentService.findAll();

        return new ResponseEntity<>(toCommentDTOList(comments), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long id){
    	Comment comment = commentService.findOne(id);
        if(comment == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(commentMapper.toDto(comment), HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO){
    	Comment comment;
        try {
        	comment = commentService.create(commentMapper.toEntity(commentDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(commentMapper.toDto(comment), HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentDTO, @PathVariable Long id){
    	Comment comment;
        try {
        	comment = commentService.update(commentMapper.toEntity(commentDTO), id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(commentMapper.toDto(comment), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        try {
        	commentService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public CommentController() {
    	commentMapper = new CommentMapper();
    }

    private List<CommentDTO> toCommentDTOList(List<Comment> comments){
        List<CommentDTO> commentsDTOs = new ArrayList<>();
        for (Comment comment : comments) {
        	commentsDTOs.add(commentMapper.toDto(comment));
        }
        return commentsDTOs;
    }
}
