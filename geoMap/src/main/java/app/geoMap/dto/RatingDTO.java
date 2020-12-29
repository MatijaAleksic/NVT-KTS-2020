package app.geoMap.dto;

import javax.validation.constraints.NotBlank;

import app.geoMap.model.User;

public class RatingDTO {

	private Long id;
	
	@NotBlank(message = "Value cannot be empty.")
	private float value;
	
	private User user;
	
	public RatingDTO() {	
		
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RatingDTO(Long id, @NotBlank(message = "Value cannot be empty.") float value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	public RatingDTO(Long id, @NotBlank(message = "Value cannot be empty.") float value, User user) {
		super();
		this.user = user;
		this.id = id;
		this.value = value;
	}

	
	public RatingDTO(@NotBlank(message = "Value cannot be empty.") float value, User user) {
		super();
		this.user = user;
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
