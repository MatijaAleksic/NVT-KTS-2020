package app.geoMap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.geoMap.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
	
	Image findByName(String name);

}
