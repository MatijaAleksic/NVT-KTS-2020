package app.geoMap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.geoMap.model.CultureSubtype;

@Repository
public interface CultureSubtypeRepository extends JpaRepository<CultureSubtype, Long>{

}
