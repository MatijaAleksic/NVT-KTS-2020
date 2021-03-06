package app.geoMap.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.geoMap.model.CultureSubtype;
import app.geoMap.model.CultureType;
import app.geoMap.repository.CultureTypeRepository;

@Service
public class CultureTypeService implements ServiceInterface<CultureType>{
	
	@Autowired
	private CultureTypeRepository cultureTypeRepository;
	

	@Override
	public List<CultureType> findAll() {
		return cultureTypeRepository.findAll();
	}

	public Page<CultureType> findAll(Pageable pageable){
		return cultureTypeRepository.findAll(pageable);
	}

	@Override
	public CultureType findOne(Long id) {
		return cultureTypeRepository.findById(id).orElse(null);
	}

	@Override
	public CultureType create(CultureType entity) throws Exception {
		if(cultureTypeRepository.findByName(entity.getName()) != null) {
			throw new Exception("Culture type with given name already exists!");
		}
		
		CultureType cultureType = new CultureType();
		//cultureType.setId(entity.getId());
		cultureType.setName(entity.getName());
		//cultureType.setCultureSubtypes(new HashSet<CultureSubtype>());
		
		cultureType = cultureTypeRepository.save(cultureType);
		return cultureType;
	}


	@Override
    public CultureType update(CultureType entity, Long id) throws Exception {
		CultureType existingCultureType =  cultureTypeRepository.findById(id).orElse(null);
        if(existingCultureType == null){
            throw new Exception("Cultural content type with given id doesn't exist");
        }
        existingCultureType.setName(entity.getName());
        existingCultureType.setCultureSubtypes(entity.getCultureSubtypes());

        /*if(cultureTypeRepository.findByNameAndIdNot(existingCultureType.getName(), id) != null){
            throw new Exception("Cultural content type with given name already exists");
        }*/
        return cultureTypeRepository.save(existingCultureType);
    }

	@Override
	public void delete(Long id) throws Exception {
		CultureType existingCultureType =  cultureTypeRepository.findById(id).orElse(null);
		if(existingCultureType == null){
            throw new Exception("Cultural content type with given id doesn't exist");
        }
		cultureTypeRepository.delete(existingCultureType);
	}
		
	
	
	
	
}