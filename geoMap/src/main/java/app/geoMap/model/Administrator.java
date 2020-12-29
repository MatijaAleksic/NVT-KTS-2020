package app.geoMap.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@DiscriminatorValue(value = "ADMINISTRATOR")
public class Administrator extends User{
	
	@PrimaryKeyJoinColumn
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Set<User> allUsers;
	
	@PrimaryKeyJoinColumn
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Set<CultureType> allCultureType;
	
	@PrimaryKeyJoinColumn
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Set<CultureSubtype> allCultureSubtype;
	
	@PrimaryKeyJoinColumn
	@OneToMany( fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<CulturalOffer> culturalOffersCreated;
	
	public Administrator() {
		
	}
	
	public Administrator(String firstName, String lastName, String userName, String password, String email) {
		super(firstName,lastName, userName, password, email);
	}
	
	public Administrator(Set<User> allUsers, Set<CultureType> allCultureType,
			Set<CultureSubtype> allCultureSubtype, Set<CulturalOffer> culturalOffersCreated) {
		super();
		this.allUsers = allUsers;
		this.allCultureType = allCultureType;
		this.allCultureSubtype = allCultureSubtype;
		this.culturalOffersCreated = culturalOffersCreated;
	}

	public Set<CulturalOffer> getCulturalOffersCreated() {
		return culturalOffersCreated;
	}

	public void setCulturalOffersCreated(Set<CulturalOffer> culturalOffersCreated) {
		this.culturalOffersCreated = culturalOffersCreated;
	}

	public Set<User> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(Set<User> allUsers) {
		this.allUsers = allUsers;
	}

	public Set<CultureType> getAllCultureType() {
		return allCultureType;
	}

	public void setAllCultureType(Set<CultureType> allCultureType) {
		this.allCultureType = allCultureType;
	}

	public Set<CultureSubtype> getAllCultureSubtype() {
		return allCultureSubtype;
	}

	public void setAllCultureSubtype(Set<CultureSubtype> allCultureSubtype) {
		this.allCultureSubtype = allCultureSubtype;
	}

	@Override
	public String toString() {
		return "Administrator [allUsers=" + allUsers + ", allCultureType="
				+ allCultureType + ", allCultureSubtype=" + allCultureSubtype + "]";
	}
	
	
	
	
}
