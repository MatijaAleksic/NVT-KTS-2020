package app.geoMap.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.geoMap.model.Moderator;
import app.geoMap.repository.CulturalOfferRepository;
import app.geoMap.repository.ModeratorRepository;

@Service
public class ModeratorService implements ServiceInterface<Moderator>{
	
	@Autowired
	private ModeratorRepository moderatorRepository;
	
	@Autowired
	private CulturalOfferService culturalOfferService;
	
	@Autowired
	private NewsService newsService;

	public List<Moderator> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Moderator findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	public Moderator create(Moderator entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public Moderator update(Moderator entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
