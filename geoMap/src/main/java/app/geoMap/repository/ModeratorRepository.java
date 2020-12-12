package app.geoMap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.geoMap.model.Moderator;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Long>{
	
	Moderator findByUsername(String username);

}
