package app.geoMap.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AdministratorDTO extends UserDTO{
	
	private Long id;
	
	public AdministratorDTO() {}
	
	public AdministratorDTO(Long id, String firstName, String lastName, String userName,  String password, String email) {
		super(id, firstName,lastName,userName,password,email);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}