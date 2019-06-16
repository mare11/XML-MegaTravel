package org.xmlws.accommodationservice.repository;

import javax.xml.xquery.XQException;

import org.springframework.stereotype.Repository;
import org.xmlws.accommodationservice.model.AdditionalService;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;

@Repository
public class AdditionalServiceRepository extends ExistXQJRepository<AdditionalService> {

	public AdditionalServiceRepository() throws XQException {
		super(new XMLEntityInformation<AdditionalService>(AdditionalService.class));
	}
}
