package app.geoMap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.geoMap.model.User;
import app.geoMap.repository.UserRepository;

import app.geoMap.model.CulturalOffer;
import app.geoMap.repository.CulturalOfferRepository;

@Service
public class UserService implements ServiceInterface<User>{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CulturalOfferRepository culturalOfferRepository;
	
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	@Override
	public User findOne(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	
	@Override
	public User create(User entity) throws Exception {
		if(userRepository.findByUsername(entity.getUserName()) != null){
            throw new Exception("User with username already exists");
        }
        return userRepository.save(entity);
	}

	@Override
	public User update(User entity, Long id) throws Exception {
		User existingUser =  userRepository.findById(id).orElse(null);
        if(existingUser == null){
            throw new Exception("User with given id doesn't exist");
        }
        existingUser.setFirstName(entity.getFirstName());
        existingUser.setLastName(entity.getLastName());
        existingUser.setUserName(entity.getUserName());
        existingUser.setPassword(entity.getPassword());
        existingUser.setEmail(entity.getEmail());
        existingUser.setCulturalOffersSubscribed(entity.getCulturalOffersSubscribed());
        
        return userRepository.save(existingUser);
	}

	@Override
	public void delete(Long id) throws Exception {
		User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser == null){
            throw new Exception("User with given id doesn't exist");
        }
        userRepository.delete(existingUser);
	}
}