package app.geoMap.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.geoMap.model.User;
import app.geoMap.repository.UserRepository;

@Service
public class UserService implements ServiceInterface<User>{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CulturalOfferService culturalOfferService;
	
	
	
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public User findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public User create(User entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public User update(User entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	
}
