package app.geoMap.dto;

import java.util.Date;
import javax.validation.constraints.NotBlank;

public class NewsDTO {
	
	private Long id;
	
	@NotBlank(message = "Title cannot be empty.")
	private String title;

	@NotBlank(message = "Creation date cannot be empty.")
	private Date creationDate;
	

	public NewsDTO() {}

	public NewsDTO(Long id, @NotBlank(message = "Title cannot be empty.") String title, @NotBlank(message = "Creation date cannot be empty.") Date creationDate) {
		super();
		this.id = id;
		this.title = title;
		this.creationDate = creationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
