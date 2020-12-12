package app.geoMap.dto;

import app.geoMap.model.User;
import javax.validation.constraints.NotBlank;

public class RatingDTO {

	private Long id;
	
	@NotBlank(message = "Value cannot be empty.")
	private float value;
	
	public RatingDTO() {	
		
	}
	
	public RatingDTO(Long id, @NotBlank(message = "Value cannot be empty.") float value) {
		super();
		this.id = id;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
}
