package app.geoMap.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "culture_type")
public class CultureType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "culture_type_id")
	private Long id;
	
	@Column(name = "name" , nullable = true , unique = true)
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	private Set<CultureSubtype> cultureSubtypes;

	public CultureType() {
		
	}
	
	public CultureType(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CultureType(Long id) {
		this.id = id;
	}
	
	public CultureType(String name) {
		this.name = name;
	}
	
	public CultureType(String name, Set<CultureSubtype> cultureSubtypes) {
		this.name = name;
		this.cultureSubtypes = cultureSubtypes;
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

	public Set<CultureSubtype> getCultureSubtypes() {
		return cultureSubtypes;
	}

	public void setCultureSubtypes(Set<CultureSubtype> cultureSubtypes) {
		this.cultureSubtypes = cultureSubtypes;
	}

	@Override
	public String toString() {
		return "CultureType [id=" + id + ", name=" + name + ", cultureSubtypes=" + cultureSubtypes + "]";
	}
	
	
	
}
