package org.xmlws.accommodationservice.repository;

import javax.xml.xquery.XQException;

import org.springframework.stereotype.Repository;
import org.xmlws.accommodationservice.model.Location;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;

@Repository
public class LocationRepository extends ExistXQJRepository<Location> {

	public LocationRepository() throws XQException {
		super(new XMLEntityInformation<Location>(Location.class));
	}
}
