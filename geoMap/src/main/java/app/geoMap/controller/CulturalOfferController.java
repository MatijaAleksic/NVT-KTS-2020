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

import app.geoMap.dto.CulturalOfferDTO;
import app.geoMap.helper.CulturalOfferMapper;
import app.geoMap.model.CulturalOffer;
import app.geoMap.service.CulturalOfferService;

@RestController
public class CulturalOfferController {
	@Autowired
    private CulturalOfferService culturalOfferService;

    private CulturalOfferMapper culturalOfferMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CulturalOfferDTO>> getAllCulturalOffers() {
        List<CulturalOffer> culturalOffers = culturalOfferService.findAll();

        return new ResponseEntity<>(toCulturalOfferDTOList(culturalOffers), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<CulturalOfferDTO> getCulturalOffer(@PathVariable Long id){
    	CulturalOffer culturalOffer = culturalOfferService.findOne(id);
        if(culturalOffer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(culturalOfferMapper.toDto(culturalOffer), HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CulturalOfferDTO> createCulturalOffer(@RequestBody CulturalOfferDTO culturalOfferDTO){
    	CulturalOffer culturalOffer;
        try {
        	culturalOffer = culturalOfferService.create(culturalOfferMapper.toEntity(culturalOfferDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(culturalOfferMapper.toDto(culturalOffer), HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CulturalOfferDTO> updateCulturalOffer(@RequestBody CulturalOfferDTO culturalOffersDTO, @PathVariable Long id){
    	CulturalOffer culturalOffer;
        try {
        	culturalOffer = culturalOfferService.update(culturalOfferMapper.toEntity(culturalOffersDTO), id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(culturalOfferMapper.toDto(culturalOffer), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCulturalOffer(@PathVariable Long id){
        try {
        	culturalOfferService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public CulturalOfferController() {
        culturalOfferMapper = new CulturalOfferMapper();
    }

    private List<CulturalOfferDTO> toCulturalOfferDTOList(List<CulturalOffer> culturalOffers){
        List<CulturalOfferDTO> culturalOfferDTOs = new ArrayList<>();
        for (CulturalOffer culturalOffer : culturalOffers) {
        	culturalOfferDTOs.add(culturalOfferMapper.toDto(culturalOffer));
        }
        return culturalOfferDTOs;
    }
	
}
