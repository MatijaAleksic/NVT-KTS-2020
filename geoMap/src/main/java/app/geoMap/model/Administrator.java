package app.geoMap.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "administrator_entity")
public class Administrator extends User{
	
	//Isto pitanje kao i kod news
	@PrimaryKeyJoinColumn
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Set<User> allUsers;
	
	@PrimaryKeyJoinColumn
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Set<Moderator> allModerators;
	
	@PrimaryKeyJoinColumn
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Set<CultureType> allCultureType;
	
	@PrimaryKeyJoinColumn
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Set<CultureSubtype> allCultureSubtype;

	
	
	
	public Administrator() {
		
	}
	
	public Administrator(Set<User> allUsers, Set<Moderator> allModerators, Set<CultureType> allCultureType,
			Set<CultureSubtype> allCultureSubtype) {
		super();
		this.allUsers = allUsers;
		this.allModerators = allModerators;
		this.allCultureType = allCultureType;
		this.allCultureSubtype = allCultureSubtype;
	}

	public Set<User> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(Set<User> allUsers) {
		this.allUsers = allUsers;
	}

	public Set<Moderator> getAllModerators() {
		return allModerators;
	}

	public void setAllModerators(Set<Moderator> allModerators) {
		this.allModerators = allModerators;
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
		return "Administrator [allUsers=" + allUsers + ", allModerators=" + allModerators + ", allCultureType="
				+ allCultureType + ", allCultureSubtype=" + allCultureSubtype + "]";
	}
	
	
	
	
}
