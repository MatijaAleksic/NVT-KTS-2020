package app.geoMap.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "culture_subtype")
public class CultureSubtype {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "culture_subtype_id")
	private Long id;
	
	@Column(name = "name" , unique = true , nullable = false)
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	private CultureType cultureType;

	
	
	public CultureSubtype() {
		
	}
	
	
	public CultureSubtype(Long id, String name, CultureType cultureType) {
		this.id = id;
		this.name = name;
		this.cultureType = cultureType;
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

	public CultureType getCultureType() {
		return cultureType;
	}

	public void setCultureType(CultureType cultureType) {
		this.cultureType = cultureType;
	}

	@Override
	public String toString() {
		return "CultureSubtype [id=" + id + ", name=" + name + ", cultureType=" + cultureType + "]";
	}
	
	
	
}
