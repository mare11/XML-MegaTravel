package org.xmlws.searchservice.repository;

import org.springframework.stereotype.Repository;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;
import org.xmlws.searchservice.model.AdditionalService;

@Repository
public class AdditionalServiceRepository extends ExistXQJRepository<AdditionalService> {

	public AdditionalServiceRepository() {
		super(new XMLEntityInformation<AdditionalService>(AdditionalService.class));
	}
}