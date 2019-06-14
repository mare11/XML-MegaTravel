package org.xmlws.authenticationservice.repository;

import javax.xml.xquery.XQException;

import org.springframework.stereotype.Repository;
import org.xmlws.authenticationservice.model.Administrator;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;

@Repository
public class AdministratorRepository extends ExistXQJRepository<Administrator> {

	public AdministratorRepository() throws XQException {
		super(new XMLEntityInformation<Administrator>(Administrator.class));
	}
}