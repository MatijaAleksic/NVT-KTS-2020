package app.geoMap.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDTO {
	
	private Long id;
	
	@NotBlank(message = "First name cannot be empty.")
	private String firstName;
	
	@NotBlank(message = "Last name cannot be empty.")
	private String lastName;
	
	@NotBlank(message = "User name cannot be empty.")
	private String username;
	
	@NotBlank(message = "Password cannot be empty.")
	private String password;
	
	@Email(message = "Email format is not valid.")
	private String email;
	
	public UserDTO() {
		
	}

	public UserDTO(Long id, @NotBlank(message = "First name cannot be empty.") String firstName, @NotBlank(message = "Last name cannot be empty.") String lastName,
						    @NotBlank(message = "User name cannot be empty.") String userName, @NotBlank(message = "Password cannot be empty.") String password,
						    @Email(message = "Email format is not valid.") String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = userName;
		this.password = password;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}