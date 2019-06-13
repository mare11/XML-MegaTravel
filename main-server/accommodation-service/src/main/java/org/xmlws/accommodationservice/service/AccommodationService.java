package org.xmlws.accommodationservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.xmlws.accommodationservice.model.Accommodation;
import org.xmlws.accommodationservice.repository.AccommodationRepository;
import org.xmlws.dataservice.catalog.CatalogRepository;

@Service
public class AccommodationService {

	@Autowired
	private AccommodationRepository accommodationRepository;

	@Autowired
	private CatalogRepository catalogRepository;

	public Accommodation findOne(Long id) {
		return accommodationRepository.findOne(id.toString());
	}

	public List<Accommodation> findAll() {
		return accommodationRepository.findAll();
	}

	public Accommodation save(Accommodation accommodation) {
		Long id = catalogRepository.getCatalogId(accommodationRepository.getRootElementName());
		accommodation.setId(id);
		return accommodationRepository.save(accommodation);
	}

	public Accommodation update(Accommodation accommodation) throws NotFoundException {
		Accommodation accommodationToUpdate = accommodationRepository.findOne(accommodation.getId().toString());
		if (accommodationToUpdate == null) {
			throw new NotFoundException();
		} else {
			accommodationToUpdate.setAccommodationTypeId(accommodation.getAccommodationTypeId());
			accommodationToUpdate.setAgentId(accommodation.getAgentId());
			accommodationToUpdate.setCancellationDays(accommodation.getCancellationDays());
			accommodationToUpdate.setCancellationPrice(accommodation.getCancellationPrice());
			accommodationToUpdate.setCategory(accommodation.getCategory());
			accommodationToUpdate.setDefaultPrice(accommodation.getDefaultPrice());
			accommodationToUpdate.setDescription(accommodation.getDescription());
			accommodationToUpdate.setFreeCancellation(accommodation.isFreeCancellation());
			accommodationToUpdate.setLocationId(accommodation.getLocationId());
			accommodationToUpdate.setNumberOfPersons(accommodation.getNumberOfPersons());
			accommodationToUpdate = accommodationRepository.save(accommodationToUpdate);
			return accommodationToUpdate;
		}
	}

	public Boolean delete(Long id) {
		Accommodation accommodation = accommodationRepository.findOne(id.toString());
		if (accommodation == null) {
			return false;
		} else {
			accommodationRepository.delete(accommodation);
			return true;
		}
	}
}
