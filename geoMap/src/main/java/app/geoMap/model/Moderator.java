package app.geoMap.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "moderator_entity")
public class Moderator extends User{
	//Ovo je jedini problem.
	@JoinColumn(name = "cultural_offer_id")
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Set<CulturalOffer> culturalOffers; //Kulturne ponude koje je moderator kreirao.
	
	
	//Dodati na dijagram
	@OneToMany(mappedBy =  "moderator" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<News> news;
	
	public Moderator() {
		
		
	}
	
	public Moderator(Set<CulturalOffer> culturalOffers, Set<News> news) {
		super();
		this.culturalOffers = culturalOffers;
		this.news = news;
	}

	public Set<CulturalOffer> getCulturalOffers() {
		return culturalOffers;
	}

	public void setCulturalOffers(Set<CulturalOffer> culturalOffers) {
		this.culturalOffers = culturalOffers;
	}

	public Set<News> getNews() {
		return news;
	}

	public void setNews(Set<News> news) {
		this.news = news;
	}

	@Override
	public String toString() {
		return "Moderator [culturalOffers=" + culturalOffers + ", news=" + news + "]";
	}
	

}
