package app.geoMap.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.geoMap.dto.RatingDTO;
import app.geoMap.helper.RatingMapper;
import app.geoMap.model.Rating;
import app.geoMap.service.RatingService;

@RestController
@RequestMapping(value =  "/api/ratings", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatingController {
	
	@Autowired
    private RatingService ratingService;

    private RatingMapper ratingMapper;

    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RatingDTO>> getAllRatings() {
        List<Rating> ratings = ratingService.findAll();

        return new ResponseEntity<>(toRatingDTOList(ratings), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<RatingDTO> getRating(@PathVariable Long id){
    	Rating rating = ratingService.findOne(id);
        if(rating == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ratingMapper.toDto(rating), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO ratingDTO){
    	Rating rating;
        try {
        	rating = ratingService.create(ratingMapper.toEntity(ratingDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ratingMapper.toDto(rating), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingDTO> updateRating(@RequestBody RatingDTO ratingDTO, @PathVariable Long id){
    	Rating rating;
        try {
        	rating = ratingService.update(ratingMapper.toEntity(ratingDTO), id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ratingMapper.toDto(rating), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")	
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteRating(@PathVariable Long id){
        try {
        	ratingService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public RatingController() {
        ratingMapper = new RatingMapper();
    }

    private List<RatingDTO> toRatingDTOList(List<Rating> ratings){
        List<RatingDTO> RatingDTO = new ArrayList<>();
        for (Rating rating: ratings) {
        	RatingDTO.add(ratingMapper.toDto(rating));
        }
        return RatingDTO;
    }
}
