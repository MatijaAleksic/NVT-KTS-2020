package app.geoMap.helper;

import  app.geoMap.dto.ModeratorDTO;
import  app.geoMap.model.Moderator;

public class ModeratorMapper implements MapperInterface<Moderator, ModeratorDTO>{

	@Override
    public Moderator toEntity(ModeratorDTO dto) {
        return new Moderator(dto.getFirstName(), dto.getLastName(), dto.getUserName(), dto.getPassword(), dto.getEmail());
    }
	
    @Override
    public ModeratorDTO toDto(Moderator entity) {
        return new ModeratorDTO(entity.getId());
    }
}

