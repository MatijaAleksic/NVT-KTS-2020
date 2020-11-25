package app.geoMap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.geoMap.model.CultureType;

@Repository
public interface CultureTypeRepository extends JpaRepository<CultureType, Long>{

}
