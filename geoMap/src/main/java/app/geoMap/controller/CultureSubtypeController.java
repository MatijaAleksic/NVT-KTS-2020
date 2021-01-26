package app.geoMap.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.geoMap.dto.CultureSubtypeDTO;
import app.geoMap.helper.CultureSubtypeMapper;
import app.geoMap.service.CultureSubtypeService;
import app.geoMap.model.CultureSubtype;

@RestController
@RequestMapping(value =  "/api/culture_subtype", produces = MediaType.APPLICATION_JSON_VALUE)
public class CultureSubtypeController {
	
	@Autowired
	private CultureSubtypeService cultureSubtypeService;
	
	private CultureSubtypeMapper cultureSubtypeMapper;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CultureSubtypeDTO>> getAllCultureSubtypes(){
		List<CultureSubtype> cultureSubtypes = cultureSubtypeService.findAll();
		
		return new ResponseEntity<>(toCultureSubtypeDTOList(cultureSubtypes),HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<CultureSubtypeDTO> getCultureSubtype( @PathVariable("id") Long id){
		CultureSubtype cultureSubtype = cultureSubtypeService.findOne(id);
		if(cultureSubtype == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(cultureSubtypeMapper.toDto(cultureSubtype), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CultureSubtypeDTO> createCultureSubtype(@RequestBody CultureSubtypeDTO culturalSubtypeDTO){
		CultureSubtype cultureSubtype;
		try {
			cultureSubtype = cultureSubtypeService.create(cultureSubtypeMapper.toEntity(culturalSubtypeDTO));
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		
		return new ResponseEntity<>(cultureSubtypeMapper.toDto(cultureSubtype), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CultureSubtypeDTO> updateCultureSubtype(@RequestBody CultureSubtypeDTO culturalSubtypeDTO, @PathVariable Long id){
		CultureSubtype cultureSubtype;
		try {
			cultureSubtype = cultureSubtypeService.update(cultureSubtypeMapper.toEntity(culturalSubtypeDTO), id);
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		
		return new ResponseEntity<>(cultureSubtypeMapper.toDto(cultureSubtype), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCultureSubtype( @PathVariable Long id){
		try {
			cultureSubtypeService.delete(id);
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public CultureSubtypeController() {
		
		cultureSubtypeMapper = new CultureSubtypeMapper();
	}

	private List<CultureSubtypeDTO> toCultureSubtypeDTOList(List<CultureSubtype> cultureSubtypes) {
		List<CultureSubtypeDTO> cultureSubtypeDTOS = new ArrayList<>();
		for (CultureSubtype cultureSubtype: cultureSubtypes) {
			cultureSubtypeDTOS.add(cultureSubtypeMapper.toDto(cultureSubtype));
		}
		return cultureSubtypeDTOS;
	}
}