package org.xmlws.accommodationservice.repository;

import javax.xml.xquery.XQException;

import org.springframework.stereotype.Repository;
import org.xmlws.accommodationservice.model.AccommodationType;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;

@Repository
public class AccommodationTypeRepository extends ExistXQJRepository<AccommodationType> {
	
	public AccommodationTypeRepository() throws XQException {
		super(new XMLEntityInformation<AccommodationType>(AccommodationType.class));
	}
}
