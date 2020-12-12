package app.geoMap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.geoMap.model.CultureSubtype;
import app.geoMap.model.CultureType;
import app.geoMap.model.User;
import app.geoMap.repository.CultureSubtypeRepository;
import app.geoMap.repository.CultureTypeRepository;

@Service
public class CultureSubtypeService implements ServiceInterface<CultureSubtype>{
	
	@Autowired
	private CultureSubtypeRepository cultureSubtypeRepository;
	
	@Autowired
	private CultureTypeService cultureTypeService;
	
	
	public List<CultureSubtype> findAll(long cultureTypeId) {
		return cultureSubtypeRepository.findByCultureTypeId(cultureTypeId);
	}

	
	public CultureSubtype findOne(Long cultureTypeId, Long id) {
		return cultureSubtypeRepository.findByCultureTypeIdAndId(cultureTypeId, id);
	}

	
	public CultureSubtype create(CultureSubtype entity, Long cultureTypeId) throws Exception {
		if(cultureSubtypeRepository.findByName(entity.getName()) != null) {
			throw new Exception("Chosen culture subtype already exists!");
		}
		CultureType cultureType = cultureTypeService.findOne(cultureTypeId);
		if(cultureType == null) {
			throw new Exception("Chosen culture type doesn't exist!");
		}
		entity.setCultureType(cultureType);
		return cultureSubtypeRepository.save(entity);
	}

	
	public CultureSubtype update(CultureSubtype entity, Long id, Long cultureTypeId) throws Exception {
		CultureSubtype existingCultureSubtype = cultureSubtypeRepository.findByCultureTypeIdAndId(cultureTypeId, id);
		if(existingCultureSubtype == null) {
			throw new Exception("Culture subtype with given id doesn't exist!");
		}
		existingCultureSubtype.setName(entity.getName());
		if(cultureSubtypeRepository.findByNameAndIdNot(existingCultureSubtype.getName(), id) != null) {
			throw new Exception("Culture subtype with given name already exist!");
		}
		return cultureSubtypeRepository.save(existingCultureSubtype);
	}

	
	public void delete(Long id, Long cultureTypeId) throws Exception {
		CultureSubtype existingCultureSubtype = cultureSubtypeRepository.findByCultureTypeIdAndId(cultureTypeId, id);
		if(existingCultureSubtype == null) {
			throw new Exception("Culture subtype with given id doesn't exist!");
		}
		cultureSubtypeRepository.delete(existingCultureSubtype);
		
	}


	@Override
	public List<CultureSubtype> findAll() {
		return cultureSubtypeRepository.findAll();
	}
	
	public Page<CultureSubtype> findAll(Pageable pageable) {
		return cultureSubtypeRepository.findAll(pageable);
	}


	@Override
	public CultureSubtype findOne(Long id) {
		return cultureSubtypeRepository.findById(id).orElse(null);
	}


	@Override
	public CultureSubtype create(CultureSubtype entity) throws Exception {
		if(cultureTypeService.findOne(entity.getId()) != null){
            throw new Exception("User with username already exists");
        }
        return cultureSubtypeRepository.save(entity);
	}


	@Override
	public CultureSubtype update(CultureSubtype entity, Long id) throws Exception {
		CultureSubtype existingSubType =  cultureSubtypeRepository.findById(id).orElse(null);
        if(existingSubType == null){
            throw new Exception("Culture subtype with given id doesn't exist");
        }
        existingSubType.setName(entity.getName());
        
        return cultureSubtypeRepository.save(existingSubType);
	}


	@Override
	public void delete(Long id) throws Exception {
		CultureSubtype existingUser = cultureSubtypeRepository.findById(id).orElse(null);
        if(existingUser == null){
            throw new Exception("Culture subtype with given id doesn't exist");
        }
        cultureSubtypeRepository.delete(existingUser);
	}
}