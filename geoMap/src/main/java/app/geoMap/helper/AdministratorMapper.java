package app.geoMap.helper;

import  app.geoMap.dto.AdministratorDTO;
import  app.geoMap.model.Administrator;


public class AdministratorMapper implements MapperInterface<Administrator, AdministratorDTO>{

	@Override
    public Administrator toEntity(AdministratorDTO dto) {
        return new Administrator(dto.getFirstName(), dto.getLastName(), dto.getUserName(), dto.getPassword(), dto.getEmail());
    }
	
    @Override
    public AdministratorDTO toDto(Administrator entity) {
        return new AdministratorDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getUsername(), entity.getPassword(), entity.getEmail());
    }
}
