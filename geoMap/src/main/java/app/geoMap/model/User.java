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

	
	private Set<Raiting> raitings;// mislim da ipak nije potrebno i treba se ispraviti na dijagramu...zbog toga nije stavljena anotacija
	
	//Dodati na dijagram
	@ManyToMany(mappedBy = "users" ,fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<CulturalOffer> culturalOffers; // Kulturne ponude na koje je user pretplacen.
	
	public User() {
		
	}

	public User(Long id, String firstName, String lastName, String userName, String password, String email,
			Set<Raiting> raitings, Set<CulturalOffer> culturalOffers) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.raitings = raitings;
		this.culturalOffers = culturalOffers;
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

	public Set<Raiting> getRaitings() {
		return raitings;
	}

	public void setRaitings(Set<Raiting> raitings) {
		this.raitings = raitings;
	}

	public Set<CulturalOffer> getCulturalOffers() {
		return culturalOffers;
	}

	public void setCulturalOffers(Set<CulturalOffer> culturalOffers) {
		this.culturalOffers = culturalOffers;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
				+ ", password=" + password + ", email=" + email + ", raitings=" + raitings + ", culturalOffers="
				+ culturalOffers + "]";
	}

	
	
		
}
