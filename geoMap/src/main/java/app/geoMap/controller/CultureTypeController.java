package app.geoMap.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.geoMap.model.CultureType;
import app.geoMap.service.CultureSubtypeService;
import app.geoMap.service.CultureTypeService;
import app.geoMap.dto.CultureSubtypeDTO;
import app.geoMap.dto.CultureTypeDTO;
import app.geoMap.helper.CultureSubtypeMapper;
import app.geoMap.helper.CultureTypeMapper;

@RestController
@RequestMapping(value =  "/api/cultural-type", produces = MediaType.APPLICATION_JSON_VALUE)
public class CultureTypeController {
	
	@Autowired
	private CultureTypeService cultureTypeService;
	
	private CultureTypeMapper cultureTypeMapper;
	
	/*
	@Autowired
	private CultureSubtypeService cultureSubtypeService;
	
	private CultureSubtypeMapper cultureSubtypeMapper;
	*/
	
	@RequestMapping(value= "/by-page",method = RequestMethod.GET)
	public ResponseEntity<Page<CultureTypeDTO>> getAllCultureTypes(Pageable pageable){
		Page<CultureType> page = cultureTypeService.findAll(pageable);
		List<CultureTypeDTO> cultureTypeDTOS = toCultureTypeDTOList(page.toList());
		Page<CultureTypeDTO> pageCultureTypeDTOS = new PageImpl<>(cultureTypeDTOS,page.getPageable(),page.getTotalElements());
		
		return new ResponseEntity<>(pageCultureTypeDTOS, HttpStatus.OK);	
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<CultureTypeDTO> getCultureType(@PathVariable Long id){
		CultureType cultureType = cultureTypeService.findOne(id);
		if(cultureType == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(cultureTypeMapper.toDto(cultureType), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CultureTypeDTO> createCultureType(@RequestBody CultureTypeDTO culturalTypeDTO){
		CultureType cultureType;
		try {
			cultureType = cultureTypeService.create(cultureTypeMapper.toEntity(culturalTypeDTO));
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		
		return new ResponseEntity<>(cultureTypeMapper.toDto(cultureType), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CultureTypeDTO> updateCultureType(@RequestBody CultureTypeDTO culturalTypeDTO, @PathVariable Long id){
		CultureType cultureType;
		try {
			cultureType = cultureTypeService.update(cultureTypeMapper.toEntity(culturalTypeDTO), id);
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		
		return new ResponseEntity<>(cultureTypeMapper.toDto(cultureType), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCultureType(@PathVariable Long id){
		try {
			cultureTypeService.delete(id);
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public CultureTypeController() {
		
		cultureTypeMapper = new CultureTypeMapper();
	}

	private List<CultureTypeDTO> toCultureTypeDTOList(List<CultureType> cultureTypes) {
		List<CultureTypeDTO> cultureTypeDTOS = new ArrayList<>();
		for (CultureType cultureType: cultureTypes) {
			cultureTypeDTOS.add(cultureTypeMapper.toDto(cultureType));
		}
		return cultureTypeDTOS;
	}
	
}