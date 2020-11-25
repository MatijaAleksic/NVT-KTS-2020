package app.geoMap.model;

import java.util.Arrays;

import javax.persistence.*;




@Entity
@Table(name = "image")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Long id;
	
	@Column(name = "name",unique = true, nullable = false)
	private String name;
	
	//@Lob - pitanje
	@Column(name = "picByte", length = 1000)
	private byte[] picByte;

	
	public Image() {
		
	}
	
	public Image(Long id, String name, byte[] picByte) {
		this.id = id;
		this.name = name;
		this.picByte = picByte;
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

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", name=" + name + ", picByte=" + Arrays.toString(picByte) + "]";
	}
	
	
	
}
