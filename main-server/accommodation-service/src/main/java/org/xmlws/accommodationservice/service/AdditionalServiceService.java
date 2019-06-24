package org.xmlws.accommodationservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlws.accommodationservice.exceptions.AdditionalServiceException;
import org.xmlws.accommodationservice.exceptions.AdditionalServiceNotFoundException;
import org.xmlws.accommodationservice.model.Accommodation;
import org.xmlws.accommodationservice.model.AdditionalService;
import org.xmlws.accommodationservice.repository.AccommodationRepository;
import org.xmlws.accommodationservice.repository.AdditionalServiceRepository;
import org.xmlws.dataservice.catalog.CatalogRepository;

@Service
public class AdditionalServiceService {

	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;

	@Autowired
	private CatalogRepository catalogRepository;

	@Autowired
	private AccommodationRepository accommodationRepository;
	
	public AdditionalService findOne(Long id) {
		return getAdditionalService(id);
	}

	public List<AdditionalService> findAll() {
		return additionalServiceRepository.findAll();
	}

	public AdditionalService save(AdditionalService newAdditionalService) {
		for(AdditionalService additionalService: findAll()){
			if (additionalService.getAdditionalServiceName().equals(newAdditionalService.getAdditionalServiceName())){
				throw new AdditionalServiceException(newAdditionalService.getAdditionalServiceName());
			}
		}
		Long id = catalogRepository.getCatalogId(additionalServiceRepository.getRootElementName());
		newAdditionalService.setId(id);
		return additionalServiceRepository.save(newAdditionalService);
	}

	public void delete(Long id) {
		for (Accommodation accommodation: accommodationRepository.findAll()){
			for (Long additionalServiceId : accommodation.getAdditionalServiceIds()){
				if(additionalServiceId.equals(id)){
					throw new AdditionalServiceException();
				}
			}
		}
		AdditionalService additionalService = getAdditionalService(id);
		additionalServiceRepository.delete(additionalService);
	}
	
	private AdditionalService getAdditionalService(Long id){
		List<AdditionalService> additionalServices = additionalServiceRepository.findWithFilter("[id='" + id + "']");
		if (additionalServices.isEmpty()) {
            throw new AdditionalServiceNotFoundException(id);
        }
        return additionalServices.get(0);
	}
}
