package org.xmlws.registrationservice.repository;

import javax.xml.xquery.XQException;

import org.springframework.stereotype.Repository;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;
import org.xmlws.registrationservice.model.User;

@Repository
public class UserRepository extends ExistXQJRepository<User> {

	public UserRepository() throws XQException {
		super(new XMLEntityInformation<User>(User.class));
	}
}