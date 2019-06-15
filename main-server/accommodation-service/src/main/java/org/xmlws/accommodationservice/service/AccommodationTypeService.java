package org.xmlws.accommodationservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlws.accommodationservice.exceptions.AccommodationTypeNotFoundException;
import org.xmlws.accommodationservice.model.AccommodationType;
import org.xmlws.accommodationservice.repository.AccommodationTypeRepository;
import org.xmlws.dataservice.catalog.CatalogRepository;

@Service
public class AccommodationTypeService {

	@Autowired
	private AccommodationTypeRepository accommodationTypeRepository;

	@Autowired
	private CatalogRepository catalogRepository;

	public AccommodationType findOne(Long id) {
		return getAccommodationType(id);
	}

	public List<AccommodationType> findAll() {
		return accommodationTypeRepository.findAll();
	}

	public AccommodationType save(AccommodationType accommodationType) {
		Long id = catalogRepository.getCatalogId(accommodationTypeRepository.getRootElementName());
		accommodationType.setId(id);
		return accommodationTypeRepository.save(accommodationType);
	}

	public void delete(Long id) {
		AccommodationType accommodationType = getAccommodationType(id);
		accommodationTypeRepository.delete(accommodationType);
	}

	private AccommodationType getAccommodationType(Long id) {
		List<AccommodationType> accommodationTypes = accommodationTypeRepository.findWithFilter("[id='" + id + "']");
		if (accommodationTypes.isEmpty()) {
			throw new AccommodationTypeNotFoundException(id);
		}
		return accommodationTypes.get(0);
	}
}
