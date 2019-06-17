package org.xmlws.searchservice.repository;

import org.springframework.stereotype.Repository;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;
import org.xmlws.searchservice.model.Location;

@Repository
public class LocationRepository extends ExistXQJRepository<Location> {

	public LocationRepository() {
		super(new XMLEntityInformation<Location>(Location.class));
	}
}