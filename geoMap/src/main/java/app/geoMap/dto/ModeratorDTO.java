package app.geoMap.dto;

public class ModeratorDTO extends UserDTO{

	private Long id;
	
	public ModeratorDTO() {	}
	
	public ModeratorDTO(Long id) {
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