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

import app.geoMap.dto.ModeratorDTO;
import app.geoMap.helper.ModeratorMapper;
import app.geoMap.model.Moderator;
import app.geoMap.service.ModeratorService;


@RestController
@RequestMapping(value =  "/api/moderators", produces = MediaType.APPLICATION_JSON_VALUE)
public class ModeratorController {

	@Autowired
    private ModeratorService ModeratorService;

    private ModeratorMapper ModeratorMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ModeratorDTO>> getAllModerators() {
        List<Moderator> moderators = ModeratorService.findAll();

        return new ResponseEntity<>(toModeratorsDTOList(moderators), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<ModeratorDTO> getModerator(@PathVariable Long id){
    	Moderator moderator = ModeratorService.findOne(id);
        if(moderator == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ModeratorMapper.toDto(moderator), HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ModeratorDTO> createModerator(@RequestBody ModeratorDTO moderatorDTO){
    	Moderator moderator;
        try {
        	moderator = ModeratorService.create(ModeratorMapper.toEntity(moderatorDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ModeratorMapper.toDto(moderator), HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ModeratorDTO> updateModerator(@RequestBody ModeratorDTO moderatorDTO, @PathVariable Long id){
    	Moderator moderator;
        try {
        	moderator = ModeratorService.update(ModeratorMapper.toEntity(moderatorDTO), id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ModeratorMapper.toDto(moderator), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteModerator(@PathVariable Long id){
        try {
        	ModeratorService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ModeratorController() {
        ModeratorMapper = new ModeratorMapper();
    }

    private List<ModeratorDTO> toModeratorsDTOList(List<Moderator> moderators){
        List<ModeratorDTO> moderatorDTOs = new ArrayList<>();
        for (Moderator moderator : moderators) {
        	moderatorDTOs.add(ModeratorMapper.toDto(moderator));
        }
        return moderatorDTOs;
    }
}