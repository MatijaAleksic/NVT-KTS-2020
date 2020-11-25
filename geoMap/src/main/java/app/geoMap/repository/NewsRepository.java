package app.geoMap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.geoMap.model.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>{

}
