package app.geoMap.helper;

import app.geoMap.model.User;

import app.geoMap.dto.UserDTO;

public class UserMapper implements MapperInterface<User, UserDTO> {

	@Override
	public User toEntity(UserDTO dto) {
		return new User(dto.getFirstName(), dto.getLastName(), dto.getUserName(), dto.getPassword(), dto.getEmail());
	}

	@Override
	public UserDTO toDto(User entity) {
		return new UserDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getUserName(), entity.getPassword(), entity.getEmail());
	}

}
