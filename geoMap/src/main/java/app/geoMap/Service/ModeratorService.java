package app.geoMap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.geoMap.model.Moderator;
import app.geoMap.repository.ModeratorRepository;

@Service
public class ModeratorService implements ServiceInterface<Moderator>{
	
	@Autowired
	private ModeratorRepository moderatorRepository;
	
	@Override
	public List<Moderator> findAll() {
		return moderatorRepository.findAll();
	}
	
	public Page<Moderator> findAll(Pageable pageable) {
		return moderatorRepository.findAll(pageable);
	}

	@Override
	public Moderator findOne(Long id) {
		return moderatorRepository.findById(id).orElse(null);
	}

	@Override
	public Moderator create(Moderator entity) throws Exception {
		if(moderatorRepository.findById(entity.getId()) != null){
            throw new Exception("Moderator with given email address already exists");
        }
        return moderatorRepository.save(entity);
	}

	@Override
	public Moderator update(Moderator entity, Long id) throws Exception {
		Moderator existingModerator =  moderatorRepository.findById(id).orElse(null);
        if(existingModerator == null){
            throw new Exception("Moderator with given id doesn't exist");
        }
        
        existingModerator.setFirstName(entity.getFirstName());
        existingModerator.setLastName(entity.getLastName());
        existingModerator.setUserName(entity.getUserName());
        existingModerator.setPassword(entity.getPassword());
        existingModerator.setEmail(entity.getEmail());
        existingModerator.setCulturalOffersSubscribed(entity.getCulturalOffersSubscribed());
        existingModerator.setCulturalOffersCreated(entity.getCulturalOffersCreated());
        
        return moderatorRepository.save(existingModerator);
	}

	@Override
	public void delete(Long id) throws Exception {
		Moderator existingModerator = moderatorRepository.findById(id).orElse(null);
        if(existingModerator == null){
            throw new Exception("Moderator with given id doesn't exist");
        }
        moderatorRepository.delete(existingModerator);
	}
}