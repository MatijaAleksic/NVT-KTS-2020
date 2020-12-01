package app.geoMap.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cultural_offer")
public class CulturalOffer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cultural_offer_id")
	private Long id;
	
	@Column(name = "name" , unique = false , nullable = false)
	private String name;
	
	@Column(name = "creation_date",unique = false , nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(name = "description",nullable = false , unique = false)
	private String description;
	
	//Izmeniti na dijagramu
	@Column(name = "email" , nullable = true , unique = false)
	private String email;
	
	@Column(name = "phone" , nullable = true , unique = false )
	private String phone;
	
	@Column(name = "longitude" ,nullable = false , unique = false)
	private float longitude;
	
	@Column(name = "latitude" , nullable = false , unique = false)
	private float latitude;
	
	@OneToMany(mappedBy = "culturalOffer", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<Raiting> raitings;	
	
	@OneToMany(mappedBy = "culturalOffer" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<News> news;
	
	
	@PrimaryKeyJoinColumn
	@ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	private CultureType cultureType;
	
	@PrimaryKeyJoinColumn
	@ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	private CultureSubtype cultureSubtype;
	
	
	
	@PrimaryKeyJoinColumn
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<Comment> comments;
	
	@PrimaryKeyJoinColumn
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL) //moglo bi i ManyToMany ali veza je usmerena pa nije bitno.
	private Set<Image> images;
	
	@ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<User> users;

	private CulturalOffer() {
		
		
	}
	
	public CulturalOffer(Long id, String name, Date creationDate, String description, String email, String phone,
			float longitude, float latitude, Set<Raiting> raitings, Set<Comment> comments, Set<News> news,
			CultureType cultureType, CultureSubtype cultureSubtype, Set<Image> images, Set<User> users) {
		super();
		this.id = id;
		this.name = name;
		this.creationDate = creationDate;
		this.description = description;
		this.email = email;
		this.phone = phone;
		this.longitude = longitude;
		this.latitude = latitude;
		this.raitings = raitings;
		this.comments = comments;
		this.news = news;
		this.cultureType = cultureType;
		this.cultureSubtype = cultureSubtype;
		this.images = images;
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public Set<Raiting> getRaitings() {
		return raitings;
	}

	public void setRaitings(Set<Raiting> raitings) {
		this.raitings = raitings;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<News> getNews() {
		return news;
	}

	public void setNews(Set<News> news) {
		this.news = news;
	}

	public CultureType getCultureType() {
		return cultureType;
	}

	public void setCultureType(CultureType cultureType) {
		this.cultureType = cultureType;
	}

	public CultureSubtype getCultureSubtype() {
		return cultureSubtype;
	}

	public void setCultureSubtype(CultureSubtype cultureSubtype) {
		this.cultureSubtype = cultureSubtype;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "CulturalOffer [id=" + id + ", name=" + name + ", creationDate=" + creationDate + ", description="
				+ description + ", email=" + email + ", phone=" + phone + ", longitude=" + longitude + ", latitude="
				+ latitude + ", raitings=" + raitings + ", comments=" + comments + ", news=" + news + ", cultureType="
				+ cultureType + ", cultureSubtype=" + cultureSubtype + ", images=" + images + ", users=" + users + "]";
	}
	
	 
	
	
}
