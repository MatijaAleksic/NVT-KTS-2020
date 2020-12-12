package app.geoMap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.geoMap.model.Image;
import app.geoMap.repository.ImageRepository;

@Service
public class ImageService implements ServiceInterface<Image>{
	
	@Autowired
	private ImageRepository imageRepository;

	
	@Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }
	
	public Page<Image> findAll(Pageable pageable) {
		return imageRepository.findAll(pageable);
	}

    @Override
    public Image findOne(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public Image create(Image entity) throws Exception {
        if(imageRepository.findByName(entity.getName()) != null){
            throw new Exception("Image with given id already exists");
        }
        return imageRepository.save(entity);
    }

    @Override
    public Image update(Image entity, Long id) throws Exception {
    	Image existingImage =  imageRepository.findById(id).orElse(null);
        if(existingImage == null){
            throw new Exception("Image with given id doesn't exist");
        }
        existingImage.setName(entity.getName());
        existingImage.setPicByte(entity.getPicByte());
        
        return imageRepository.save(existingImage);
    }

    @Override
    public void delete(Long id) throws Exception {
    	Image existingImage = imageRepository.findById(id).orElse(null);
        if(existingImage == null){
            throw new Exception("Image with given id doesn't exist");
        }
        imageRepository.delete(existingImage);
    }
	
}
