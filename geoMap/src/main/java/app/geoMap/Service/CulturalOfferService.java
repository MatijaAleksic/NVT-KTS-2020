package app.geoMap.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.geoMap.model.CulturalOffer;
import app.geoMap.repository.CulturalOfferRepository;

@Service
public class CulturalOfferService implements ServiceInterface<CulturalOffer>{


	@Autowired
	private CulturalOfferRepository culturalOfferRepository;
	
	@Autowired
	private RaitingService raitingService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private CultureTypeService CultureTypeService;
	
	@Autowired 
	private CultureSubtypeService cultureSubtypeService;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private UserService userService;
	
	
	
	public List<CulturalOffer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


	public CulturalOffer findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	public CulturalOffer create(CulturalOffer entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public CulturalOffer update(CulturalOffer entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
}
