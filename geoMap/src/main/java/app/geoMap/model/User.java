package app.geoMap.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "user_table")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
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
	
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<CulturalOffer> culturalOffersSubscribed;
	
	
	
	@Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

	
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

	public Timestamp getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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
		Timestamp now = new Timestamp(new Date().getTime());
        this.setLastPasswordResetDate(now);
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

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}	
}
