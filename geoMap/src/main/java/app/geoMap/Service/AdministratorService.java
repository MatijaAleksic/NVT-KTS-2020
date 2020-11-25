package app.geoMap.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.geoMap.model.Administrator;
import app.geoMap.repository.CultureSubtypeRepository;
import app.geoMap.repository.CultureTypeRepository;
import app.geoMap.repository.ModeratorRepository;
import app.geoMap.repository.UserRepository;

@Service
public class AdministratorService implements ServiceInterface<Administrator>{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModeratorService moderatorService;
	
	@Autowired
	private CultureTypeService cultureTypeService;
	
	@Autowired
	private CultureSubtypeService cultureSubtypeService;

	
	public List<Administrator> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


	public Administrator findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Administrator create(Administrator entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public Administrator update(Administrator entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
