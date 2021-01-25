package app.geoMap.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.geoMap.dto.ImageDTO;
import app.geoMap.dto.UserDTO;
import app.geoMap.helper.ImageMapper;
import app.geoMap.model.Image;
import app.geoMap.model.User;
import app.geoMap.service.ImageService;

@RestController
@RequestMapping(value =  "/api/images", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {


	@Autowired
    private ImageService ImageService;

    private ImageMapper ImageMapper;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        List<Image> images = ImageService.findAll();

        return new ResponseEntity<>(toImagesDTOList(images), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<ImageDTO> getImage(@PathVariable Long id){
    	Image image = ImageService.findOne(id);
        if(image == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ImageMapper.toDto(image), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value= "/by-page",method = RequestMethod.GET)
    public ResponseEntity<List<ImageDTO>> getAllImages(Pageable pageable) {
        Page<Image> page = ImageService.findAll(pageable);
        List<ImageDTO> imageDTOS = toImagesDTOList(page.toList());
        //Page<UserDTO> pageUserDTOS = new PageImpl<>(userDTOS,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(imageDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImageDTO> createImage(@RequestBody ImageDTO imageDTO){
    	Image image;
        try {
        	image = ImageService.create(ImageMapper.toEntity(imageDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ImageMapper.toDto(image), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImageDTO> updateImage(@RequestBody ImageDTO imageDTO, @PathVariable Long id){
    	Image image;
        try {
        	image = ImageService.update(ImageMapper.toEntity(imageDTO), id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ImageMapper.toDto(image), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteImage(@PathVariable Long id){
        try {
        	ImageService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ImageController() {
        ImageMapper = new ImageMapper();
    }

    private List<ImageDTO> toImagesDTOList(List<Image> images){
        List<ImageDTO> imageDTOs = new ArrayList<>();
        for (Image image : images) {
        	imageDTOs.add(ImageMapper.toDto(image));
        }
        return imageDTOs;
    }
}
