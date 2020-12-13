package app.geoMap.dto;

import javax.validation.constraints.NotBlank;
	
public class CommentDTO {

	private Long id;
	
	@NotBlank(message = "Text cannot be empty.")
	private String text;

	public CommentDTO() {}
	
	public CommentDTO(Long id, @NotBlank(message = "Text cannot be empty.") String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}