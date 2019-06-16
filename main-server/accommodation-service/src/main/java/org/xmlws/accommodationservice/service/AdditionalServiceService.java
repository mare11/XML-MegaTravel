package org.xmlws.accommodationservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlws.accommodationservice.exceptions.AdditionalServiceNotFoundException;
import org.xmlws.accommodationservice.model.AdditionalService;
import org.xmlws.accommodationservice.repository.AdditionalServiceRepository;
import org.xmlws.dataservice.catalog.CatalogRepository;

@Service
public class AdditionalServiceService {

	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;

	@Autowired
	private CatalogRepository catalogRepository;

	public AdditionalService findOne(Long id) {
		return getAdditionalService(id);
	}

	public List<AdditionalService> findAll() {
		return additionalServiceRepository.findAll();
	}

	public AdditionalService save(AdditionalService additionalService) {
		Long id = catalogRepository.getCatalogId(additionalServiceRepository.getRootElementName());
		additionalService.setId(id);
		return additionalServiceRepository.save(additionalService);
	}

	public void delete(Long id) {
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
