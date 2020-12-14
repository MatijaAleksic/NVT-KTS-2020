package app.geoMap.helper;

import app.geoMap.dto.CultureSubtypeDTO;
import app.geoMap.model.CultureSubtype;

public class CultureSubtypeMapper implements MapperInterface<CultureSubtype, CultureSubtypeDTO> {
	
	@Override
	public CultureSubtype toEntity(CultureSubtypeDTO dto) {
		return new CultureSubtype(dto.getName());
	}
	@Override
	public CultureSubtypeDTO toDto(CultureSubtype entity) {
		return new CultureSubtypeDTO(entity.getId(), entity.getName());
	}

}