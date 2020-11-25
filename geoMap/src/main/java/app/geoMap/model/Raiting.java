package app.geoMap.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "raiting")
public class Raiting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "raiting_id")
	private Long id;
	
	@Column(name = "value" , nullable = false ,unique = false)
	private float value;
	
	@PrimaryKeyJoinColumn
	@OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	private User user;
	
	
	@ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	private CulturalOffer culturalOffer;

	
	private Raiting() {	
		
	}
	
	public Raiting(Long id, float value, User user, CulturalOffer culturalOffer) {
		super();
		this.id = id;
		this.value = value;
		this.user = user;
		this.culturalOffer = culturalOffer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CulturalOffer getCulturalOffer() {
		return culturalOffer;
	}

	public void setCulturalOffer(CulturalOffer culturalOffer) {
		this.culturalOffer = culturalOffer;
	}

	
	@Override
	public String toString() {
		return "Raiting [id=" + id + ", value=" + value + ", user=" + user + ", culturalOffer=" + culturalOffer + "]";
	}
	
	
	
}
