package app.geoMap.helper;

import  app.geoMap.dto.RatingDTO;
import  app.geoMap.model.Rating;


public class RatingMapper implements MapperInterface<Rating, RatingDTO>{

	@Override
    public Rating toEntity(RatingDTO dto) {
        return new Rating(dto.getValue());
    }
	
    @Override
    public RatingDTO toDto(Rating entity) {
        return new RatingDTO(entity.getId(), entity.getValue());
    }
}