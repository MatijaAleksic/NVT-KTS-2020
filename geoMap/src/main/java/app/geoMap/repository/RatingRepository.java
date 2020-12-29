package app.geoMap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.geoMap.model.Rating;
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>{
	
	Rating findByUser_Email(String email);
	
	Rating findByUser_Id(Long id);
}
