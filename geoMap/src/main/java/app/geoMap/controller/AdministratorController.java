package app.geoMap.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.geoMap.dto.AdministratorDTO;
import app.geoMap.helper.AdministratorMapper;
import app.geoMap.model.Administrator;
import app.geoMap.service.AdministratorService;

@RestController
@RequestMapping(value =  "/api/administrators", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdministratorController {
	@Autowired
    private AdministratorService administratorService;

    private AdministratorMapper administratorMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AdministratorDTO>> getAllAdministrators() {
        List<Administrator> administrators = administratorService.findAll();

        return new ResponseEntity<>(toAdministratorsDTOList(administrators), HttpStatus.OK);
    }
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<AdministratorDTO> getAdministrator(@PathVariable Long id){
    	Administrator administrator = administratorService.findOne(id);
        if(administrator == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(administratorMapper.toDto(administrator), HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministratorDTO> createAdministrator(@RequestBody AdministratorDTO administratorDTO){
    	Administrator administrator;
        try {
        	administrator = administratorService.create(administratorMapper.toEntity(administratorDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(administratorMapper.toDto(administrator), HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministratorDTO> updateAdministrator(@RequestBody AdministratorDTO administratorDTO, @PathVariable Long id){
    	Administrator administrator;
        try {
        	administrator = administratorService.update(administratorMapper.toEntity(administratorDTO), id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(administratorMapper.toDto(administrator), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAdministrator(@PathVariable Long id){
        try {
        	administratorService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public AdministratorController() {
    	administratorMapper = new AdministratorMapper();
    }

    private List<AdministratorDTO> toAdministratorsDTOList(List<Administrator> administrators){
        List<AdministratorDTO> administratorsDTOs = new ArrayList<>();
        for (Administrator administrator : administrators) {
        	administratorsDTOs.add(administratorMapper.toDto(administrator));
        }
        return administratorsDTOs;
    }
}
