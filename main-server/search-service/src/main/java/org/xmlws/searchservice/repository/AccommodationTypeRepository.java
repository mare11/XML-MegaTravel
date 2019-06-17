package org.xmlws.searchservice.repository;

import org.springframework.stereotype.Repository;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;
import org.xmlws.searchservice.model.AccommodationType;

@Repository
public class AccommodationTypeRepository extends ExistXQJRepository<AccommodationType> {

	public AccommodationTypeRepository() {
		super(new XMLEntityInformation<AccommodationType>(AccommodationType.class));
	}
}