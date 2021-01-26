package app.geoMap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.geoMap.model.Rating;
import app.geoMap.model.User;
import app.geoMap.repository.RatingRepository;
import app.geoMap.repository.UserRepository;

@Service
public class RatingService implements ServiceInterface<Rating>{

	@Autowired
	private RatingRepository raitingRepository;
	
	@Autowired
	private UserRepository userRepository;


    @Override
    public List<Rating> findAll() {
        return raitingRepository.findAll();
    }
    
    public Page<Rating> findAll(Pageable pageable) {
		return raitingRepository.findAll(pageable);
	}
   

    @Override
    public Rating findOne(Long id) {
        return raitingRepository.findById(id).orElse(null);
    }

    public Rating create(Rating entity, Long userId) throws Exception {
        if(raitingRepository.findById(entity.getId()).orElse(null) != null){
            throw new Exception("Rating with given id already exists");
        }
        
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new Exception("Cultural Offer category doesn't exist.");
        }
        entity.setUser(user);
        
        return raitingRepository.save(entity);
    }

    @Override
    public Rating update(Rating entity, Long id) throws Exception {
    	Rating existingRating =  raitingRepository.findById(id).orElse(null);
        if(existingRating == null){
            throw new Exception("Rating with given id doesn't exist");
        }
        existingRating.setUser(entity.getUser());;
        existingRating.setValue(entity.getValue());

        return raitingRepository.save(existingRating);
    }

    @Override
    public void delete(Long id) throws Exception {
    	Rating existingRating = raitingRepository.findById(id).orElse(null);
        if(existingRating == null){
            throw new Exception("User with given id doesn't exist");
        }
        raitingRepository.delete(existingRating);
    }

	@Override
	public Rating create(Rating entity) throws Exception {
		if(raitingRepository.findByValue(entity.getValue()) != null){
            throw new Exception("Rating with given id already exists");
        } 
        return raitingRepository.save(entity);
	}
}