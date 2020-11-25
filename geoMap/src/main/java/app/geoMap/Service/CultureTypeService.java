package app.geoMap.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.geoMap.model.CultureType;
import app.geoMap.repository.CultureSubtypeRepository;
import app.geoMap.repository.CultureTypeRepository;

@Service
public class CultureTypeService implements ServiceInterface<CultureType>{
	
	@Autowired
	private CultureTypeRepository cultureTypeRepository;
	
	@Autowired
	private CultureSubtypeService cultureSubtypeService;


	public List<CultureType> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


	public CultureType findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	public CultureType create(CultureType entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public CultureType update(CultureType entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
