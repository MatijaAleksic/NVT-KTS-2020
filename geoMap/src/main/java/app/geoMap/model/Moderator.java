package app.geoMap.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "moderator")
public class Moderator extends User{

	@PrimaryKeyJoinColumn
	@OneToMany( fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<CulturalOffer> culturalOffersCreated; //Kulturne ponude na koje je moderator kreirao.
	
	public Moderator() {
		
		
	}
	
	public Moderator(String firstName, String lastName, String userName, String password, String email) {
		super(firstName,lastName, userName, password, email);
	}
	
	public Moderator(Set<CulturalOffer> culturalOffersCreated) {
		super();
		this.culturalOffersCreated = culturalOffersCreated;
	}

	public Set<CulturalOffer> getCulturalOffersCreated() {
		return culturalOffersCreated;
	}

	public void setCulturalOffersCreated(Set<CulturalOffer> culturalOffersCreated) {
		this.culturalOffersCreated = culturalOffersCreated;
	}

	@Override
	public String toString() {
		return "Moderator [culturalOffersCreated=" + culturalOffersCreated + "]";
	}
}