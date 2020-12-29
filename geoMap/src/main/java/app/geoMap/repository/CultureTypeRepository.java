package app.geoMap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.geoMap.model.CultureType;

@Repository
public interface CultureTypeRepository extends JpaRepository<CultureType, Long>{
	
	CultureType findByName(String name);
	
	CultureType findByNameAndIdNot(String name, Long id);
	
	

}