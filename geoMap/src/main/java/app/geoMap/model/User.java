package app.geoMap.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	
	@Column(name = "first_name" , nullable = false , unique = false)
	private String firstName;
	
	@Column(name = "last_name" , unique = false , nullable = false)
	private String lastName;
	
	@Column(name = "user_name" , unique = false , nullable = false)
	private String userName;
	
	@Column(name = "pasword"  , unique = false , nullable = false)
	private String password;
	
	@Column(name = "email" , unique = false , nullable = false)
	private String email;

	//Dodati na dijagram
	@OneToMany(mappedBy = "users" ,fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<CulturalOffer> culturalOffersSubscribed; // Kulturne ponude na koje je user pretplacen. SUBSCRIBED
	
	public User() {
		
	}
	
	public User(String firstName, String lastName, String userName, String password, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

	public User(String firstName, String lastName, String userName, String password, String email, Set<CulturalOffer> culturalOffersSubscribed) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.culturalOffersSubscribed = culturalOffersSubscribed;
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
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Set<CulturalOffer> getCulturalOffersSubscribed() {
		return culturalOffersSubscribed;
	}

	public void setCulturalOffersSubscribed(Set<CulturalOffer> culturalOffersSubscribed) {
		this.culturalOffersSubscribed = culturalOffersSubscribed;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
				+ ", password=" + password + ", email=" + email + ", culturalOffers="
				+ culturalOffersSubscribed + "]";
	}

	
	
		
}
