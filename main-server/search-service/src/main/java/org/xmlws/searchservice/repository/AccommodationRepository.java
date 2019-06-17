package org.xmlws.searchservice.repository;

import org.springframework.stereotype.Repository;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;
import org.xmlws.searchservice.model.Accommodation;

@Repository
public class AccommodationRepository extends ExistXQJRepository<Accommodation> {

	public AccommodationRepository() {
		super(new XMLEntityInformation<Accommodation>(Accommodation.class));
	}
}