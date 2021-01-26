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
	
	@Column(name = "name" , unique = false , nullable = true)
	private String name;
	
	@Column(name = "creation_date",unique = false , nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(name = "description",nullable = true , unique = false)
	private String description;
	
	@Column(name = "email" , nullable = true , unique = false)
	private String email;
	
	@Column(name = "phone" , nullable = true , unique = false )
	private String phone;
	
	@Column(name = "longitude" ,nullable = true , unique = false)
	private Float longitude;
	
	@Column(name = "latitude" , nullable = true , unique = false)
	private Float latitude;
	
	@PrimaryKeyJoinColumn
	@ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Administrator administrator;
	
	@OneToMany(mappedBy = "culturalOffer", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<Rating> ratings;
	
	//@PrimaryKeyJoinColumn
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<Comment> comments;
	
	@OneToMany(mappedBy = "culturalOffer" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<News> news;
	
	@PrimaryKeyJoinColumn
	@ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	private CultureType cultureType;
	
	@PrimaryKeyJoinColumn
	@ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	private CultureSubtype cultureSubtype;
	
	//@PrimaryKeyJoinColumn
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<Image> images;

	

	public CulturalOffer() {}
	
	public CulturalOffer(String name,  float longitude, float latitude)
	{
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	
	public CulturalOffer(String name, Date creationDate, String description, String email, String phone,
			float longitude, float latitude, Administrator administrator, Set<Rating> ratings, Set<Comment> comments, Set<News> news,
			CultureType cultureType, CultureSubtype cultureSubtype, Set<Image> images) {
		super();
		this.name = name;
		this.creationDate = creationDate;
		this.description = description;
		this.email = email;
		this.phone = phone;
		this.longitude = longitude;
		this.latitude = latitude;
		this.administrator = administrator;
		this.ratings = ratings;
		this.comments = comments;
		this.news = news;
		this.cultureType = cultureType;
		this.cultureSubtype = cultureSubtype;
		this.images = images;
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

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> raitings) {
		this.ratings = raitings;
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


	@Override
	public String toString() {
		return "CulturalOffer [id=" + id + ", name=" + name + ", creationDate=" + creationDate + ", description="
				+ description + ", email=" + email + ", phone=" + phone + ", longitude=" + longitude + ", latitude="
				+ latitude + ", administrator =" + administrator + ", raitings=" + ratings + ", comments=" + comments + ", news=" + news + ", cultureType="
				+ cultureType + ", cultureSubtype=" + cultureSubtype + ", images=" + images + "]";
	}

	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}

}
