package app.geoMap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.geoMap.model.Administrator;
import app.geoMap.repository.AdministratorRepository;

@Service
public class AdministratorService implements ServiceInterface<Administrator>{

	@Autowired
	private AdministratorRepository administratorRepository;

	@Override
	public List<Administrator> findAll() {
		return administratorRepository.findAll();
	}
	
	public Page<Administrator> findAll(Pageable pageable) {
		return administratorRepository.findAll(pageable);
	}
	
	@Override
	public Administrator findOne(Long id) {
		return administratorRepository.findById(id).orElse(null);
	}

	@Override
	public Administrator create(Administrator entity) throws Exception {
		if(administratorRepository.findById(entity.getId()) != null){
            throw new Exception("User with given email address already exists");
        }
        return administratorRepository.save(entity);
	}

	@Override
	public Administrator update(Administrator entity, Long id) throws Exception {
		Administrator existingUser =  administratorRepository.findById(id).orElse(null);
        if(existingUser == null){
            throw new Exception("User with given id doesn't exist");
        }
        existingUser.setPassword(entity.getPassword());
        return administratorRepository.save(existingUser);
	}

	@Override
	public void delete(Long id) throws Exception {
		Administrator existingUser = administratorRepository.findById(id).orElse(null);
        if(existingUser == null){
            throw new Exception("User with given id doesn't exist");
        }
        administratorRepository.delete(existingUser);
	}
	
}
