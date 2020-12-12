package app.geoMap.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

public class CulturalOfferDTO {

	private Long id;
	
	@NotBlank(message = "Name cannot be empty.")
	private String name;

	@NotBlank(message = "Date created cannot be empty.")
	private Date creationDate;

	@NotBlank(message = "Longitude cannot be empty.")
	private float longitude;

	@NotBlank(message = "Latitude cannot be empty.")
	private float latitude;
	
	public CulturalOfferDTO() { }
	
	public CulturalOfferDTO(Long id, @NotBlank(message = "Name cannot be empty.") String name, @NotBlank(message = "Date created cannot be empty.") Date creationDate, @NotBlank(message = "Longitude cannot be empty.") float longitude,@NotBlank(message = "Latitude cannot be empty.") float latitude) {
		super();
		this.id = id;
		this.name = name;
		this.creationDate = creationDate;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
}