package app.geoMap.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.geoMap.model.Image;
import app.geoMap.repository.ImageRepository;

@Service
public class ImageService implements ServiceInterface<Image>{
	
	@Autowired
	private ImageRepository imageRepository;

	
	public List<Image> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


	public Image findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	public Image create(Image entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public Image update(Image entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
