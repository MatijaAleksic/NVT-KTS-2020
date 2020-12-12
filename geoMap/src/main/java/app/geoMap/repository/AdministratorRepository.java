package app.geoMap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.geoMap.model.Administrator;
import app.geoMap.model.Moderator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
	
	Administrator findByUsername(String username);
}
