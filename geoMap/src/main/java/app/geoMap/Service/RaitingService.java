package app.geoMap.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.geoMap.model.Raiting;
import app.geoMap.repository.RaitingRepository;

@Service
public class RaitingService implements ServiceInterface<Raiting>{

	@Autowired
	private RaitingRepository raitingRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CulturalOfferService culturalOfferService;
	
	public List<Raiting> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Raiting findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	public Raiting create(Raiting entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Raiting update(Raiting entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
