package app.geoMap.helper;

import app.geoMap.dto.CultureTypeDTO;
import app.geoMap.model.CultureType;

public class CultureTypeMapper implements MapperInterface<CultureType, CultureTypeDTO> {
	
	@Override
	public CultureType toEntity(CultureTypeDTO dto) {
		return new CultureType(dto.getName());
	}
	
	
	@Override
	public CultureTypeDTO toDto(CultureType entity) {
		return new CultureTypeDTO(entity.getId(), entity.getName());
	}

}