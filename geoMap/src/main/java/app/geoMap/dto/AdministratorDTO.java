package app.geoMap.dto;

public class AdministratorDTO extends UserDTO{
	
	private Long id;
	
	public AdministratorDTO() {}
	
	public AdministratorDTO(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}