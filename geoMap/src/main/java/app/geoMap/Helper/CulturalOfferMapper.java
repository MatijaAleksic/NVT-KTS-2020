package app.geoMap.helper;

import app.geoMap.dto.CulturalOfferDTO;
import app.geoMap.model.CulturalOffer;


public class CulturalOfferMapper implements MapperInterface<CulturalOffer, CulturalOfferDTO>{

	@Override
	public CulturalOffer toEntity(CulturalOfferDTO dto) {
		return new CulturalOffer(dto.getName(), dto.getCreationDate(), dto.getLongitude(), dto.getLatitude());
	}

	@Override
	public CulturalOfferDTO toDto(CulturalOffer entity) {
        return new CulturalOfferDTO(entity.getId(),entity.getName(), entity.getCreationDate(), entity.getLongitude(), entity.getLatitude());
	}
}