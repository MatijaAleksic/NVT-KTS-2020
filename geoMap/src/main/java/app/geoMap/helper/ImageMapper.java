package app.geoMap.helper;

import app.geoMap.dto.ImageDTO;
import app.geoMap.model.Image;

public class ImageMapper implements MapperInterface<Image, ImageDTO> {
	
	@Override
	public Image toEntity(ImageDTO dto) {
		return new Image(dto.getName());
	}
	
	@Override
	public ImageDTO toDto(Image entity) {
		return new ImageDTO(entity.getId(), entity.getName());
	}

}