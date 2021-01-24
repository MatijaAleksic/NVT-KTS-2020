package app.geoMap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.geoMap.model.CulturalOffer;
import app.geoMap.repository.CulturalOfferRepository;

@Service
public class CulturalOfferService implements ServiceInterface<CulturalOffer>{

	@Autowired
	private CulturalOfferRepository culturalOfferRepository;
	
	@Override
	public List<CulturalOffer> findAll() {
		return culturalOfferRepository.findAll();
	}

	public Page<CulturalOffer> findAll(Pageable pageable) {
        return culturalOfferRepository.findAll(pageable);
    }
	
	@Override
	public CulturalOffer findOne(Long id) {
		return culturalOfferRepository.findById(id).orElse(null);
	}

	@Override
	public CulturalOffer create(CulturalOffer entity) throws Exception {
		if(culturalOfferRepository.findByName(entity.getName()) != null) {
			throw new Exception("Chosen culture subtype already exists!");
		}

		entity.setName(entity.getName());
		return culturalOfferRepository.save(entity);
	}

	@Override
	public CulturalOffer update(CulturalOffer entity, Long id) throws Exception {
		/*if(culturalOfferRepository.findById(entity.getId()) != null){
            throw new Exception("Cultural Offer with given id already exists");
        }
		
        return culturalOfferRepository.save(entity);*/
		CulturalOffer existingCulturalOffer= culturalOfferRepository.findById(id).orElse(null);
        if(existingCulturalOffer == null){
            throw new Exception("ultural Offer with given id doesn't exist");
        }
        existingCulturalOffer.setName(entity.getName());
        
        return culturalOfferRepository.save(entity);
        
	}

	@Override
	public void delete(Long id) throws Exception {
		CulturalOffer existingCulturalOffer= culturalOfferRepository.findById(id).orElse(null);
        if(existingCulturalOffer == null){
            throw new Exception("ultural Offer with given id doesn't exist");
        }
        
        culturalOfferRepository.delete(existingCulturalOffer);
		
	}
}