package org.xmlws.accommodationservice.repository;

import javax.xml.xquery.XQException;

import org.springframework.stereotype.Repository;
import org.xmlws.accommodationservice.model.Accommodation;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;

@Repository
public class AccommodationRepository extends ExistXQJRepository<Accommodation> {

	public AccommodationRepository() throws XQException {
		super(new XMLEntityInformation<Accommodation>(Accommodation.class));
	}
}
