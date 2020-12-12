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
@Table(name = "rating")
public class Rating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rating_id")
	private Long id;
	
	@Column(name = "value" , nullable = false ,unique = false)
	private float value;
	
	@PrimaryKeyJoinColumn
	@OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	private User user;
	
	public Rating() {	
		
	}
	
	public Rating(float value) {
		super();
		this.value = value;
	}
	
	public Rating(float value, User user) {
		super();
		this.value = value;
		this.user = user;
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

	@Override
	public String toString() {
		return "Raiting [id=" + id + ", value=" + value + ", user=" + user + "]";
	}
}