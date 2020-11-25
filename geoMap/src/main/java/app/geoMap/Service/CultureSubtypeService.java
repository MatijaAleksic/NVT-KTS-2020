package app.geoMap.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.geoMap.model.CultureSubtype;
import app.geoMap.repository.CultureSubtypeRepository;

@Service
public class CultureSubtypeService implements ServiceInterface<CultureSubtype>{
	
	@Autowired
	private CultureSubtypeRepository cultureSubtypeRepository;
	
	@Autowired
	private CultureTypeService cultureTypeService;
	
	
	public List<CultureSubtype> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public CultureSubtype findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public CultureSubtype create(CultureSubtype entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public CultureSubtype update(CultureSubtype entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
}
