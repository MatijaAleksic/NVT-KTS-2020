package app.geoMap.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.geoMap.model.News;
import app.geoMap.repository.CulturalOfferRepository;
import app.geoMap.repository.ImageRepository;
import app.geoMap.repository.ModeratorRepository;
import app.geoMap.repository.NewsRepository;

@Service
public class NewsService implements ServiceInterface<News>{
	
	@Autowired
	private NewsRepository newsRepository;
	
	@Autowired
	private CulturalOfferService culturalOfferService;
	
	@Autowired
	private ModeratorService moderatorService;
	
	@Autowired
	private ImageService imageService;

	
	public List<News> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public News findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	public News create(News entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public News update(News entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
