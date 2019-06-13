package org.xmlws.accommodationservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
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
		return additionalServiceRepository.findOne(id.toString());
	}

	public List<AdditionalService> findAll() {
		return additionalServiceRepository.findAll();
	}

	public AdditionalService save(AdditionalService additionalService) {
		Long id = catalogRepository.getCatalogId(additionalServiceRepository.getRootElementName());
		additionalService.setId(id);
		return additionalServiceRepository.save(additionalService);
	}

	public void delete(Long id) throws NotFoundException {
		AdditionalService additionalService = additionalServiceRepository.findOne(id.toString());
		if (additionalService == null) {
			throw new NotFoundException();
		} else {
			additionalServiceRepository.delete(additionalService);
		}
	}
}
