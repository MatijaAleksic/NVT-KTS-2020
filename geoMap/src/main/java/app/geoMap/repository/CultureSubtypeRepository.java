package app.geoMap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.geoMap.model.CultureSubtype;

@Repository
public interface CultureSubtypeRepository extends JpaRepository<CultureSubtype, Long>{
	
	CultureSubtype findByName(String name);
	
	CultureSubtype findByNameAndIdNot(String name, Long id);
	
	List<CultureSubtype> findByCultureTypeId(Long cultureTypeId);
	
	CultureSubtype findByCultureTypeIdAndId(Long cultureTypeId, Long id);

}