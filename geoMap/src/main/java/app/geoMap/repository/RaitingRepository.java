package app.geoMap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.geoMap.model.Raiting;

@Repository
public interface RaitingRepository extends JpaRepository<Raiting, Long>{

}
