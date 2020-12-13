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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "news")
public class News {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "news_id")
	private Long id;
	
	@Column(name="title" , nullable = false, unique = false)
	private String title;
	
	@Column(name = "text" , nullable = false , unique = false)
	private String text;
	
	@Column(name="cretion_date" , nullable = false , unique = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL )
	private CulturalOffer culturalOffer;
	
	@ManyToOne (fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	private Administrator administrator;
	
//	@JoinColumn(name = "image_id") ...pitanje-JoinColumn ili PrimaryKeyJoinColumn?
	@PrimaryKeyJoinColumn
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<Image> images;


		
	public News() {}
	
	public News(String title, Date creationDate) {
		super();
		this.title = title;
		this.creationDate = creationDate;
	}

	public News(String title, String text, Date creationDate, CulturalOffer culturalOffer, Administrator administrator,
			Set<Image> images) {
		super();
		this.title = title;
		this.text = text;
		this.creationDate = creationDate;
		this.culturalOffer = culturalOffer;
		this.administrator = administrator;
		this.images = images;
	}


	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}
	

	public CulturalOffer getCulturalOffer() {
		return culturalOffer;
	}

	public void setCulturalOffer(CulturalOffer culturalOffer) {
		this.culturalOffer = culturalOffer;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", text=" + text + ", creationDate=" + creationDate
				+ ", culturalOffer=" + culturalOffer + ", administrator=" + administrator + ", images=" + images + "]";
	}
}