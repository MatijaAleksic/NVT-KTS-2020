package app.geoMap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.geoMap.model.News;
import app.geoMap.model.Administrator;
import app.geoMap.model.CulturalOffer;
import app.geoMap.repository.NewsRepository;
import app.geoMap.repository.AdministratorRepository;
import app.geoMap.repository.CulturalOfferRepository;

@Service
public class NewsService implements ServiceInterface<News>{
	
	@Autowired
	private NewsRepository newsRepository;
	
	@Autowired
	private AdministratorRepository administratorRepository;

	@Autowired
	private CulturalOfferRepository culturalOfferRepository;


	
	@Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }
	
	public Page<News> findAll(Pageable pageable) {
		return newsRepository.findAll(pageable);
	}

    @Override
    public News findOne(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    public News create(News entity, Long culturalOfferId, Long administratorId) throws Exception {
        if(newsRepository.findById(entity.getId()).orElse(null) != null){
            throw new Exception("News with given id already exists");
        }
		CulturalOffer culturalOffer = culturalOfferRepository.findById(culturalOfferId).orElse(null);
        if(culturalOffer == null) {
            throw new Exception("Cultural Offer category doesn't exist.");
        }
        entity.setCulturalOffer(culturalOffer);
        
		Administrator administrator = administratorRepository.findById(administratorId).orElse(null);
        if(administrator == null) {
            throw new Exception("Chosen Moderator doesn't exist.");
        }
        entity.setAdministrator(administrator);
        
        return newsRepository.save(entity);
    }

    @Override
    public News update(News entity, Long id) throws Exception {
    	News existingNews =  newsRepository.findById(id).orElse(null);
        if(existingNews == null){
            throw new Exception("News with given id doesn't exist");
        }
        existingNews.setCreationDate(entity.getCreationDate());
        existingNews.setCulturalOffer(entity.getCulturalOffer());
        existingNews.setImages(entity.getImages());
        existingNews.setAdministrator(entity.getAdministrator());
        existingNews.setText(entity.getText());
        existingNews.setTitle(entity.getText());

        return newsRepository.save(existingNews);
    }

    @Override
    public void delete(Long id) throws Exception {
    	News existingNews = newsRepository.findById(id).orElse(null);
        if(existingNews == null){
            throw new Exception("News with given id doesn't exist");
        }
        newsRepository.delete(existingNews);
    }

	@Override
	public News create(News entity) throws Exception {
		if(newsRepository.findById(entity.getId()).orElse(null) != null){
            throw new Exception("News with given id already exists");
		} 
        return newsRepository.save(entity);
	}
	
}
