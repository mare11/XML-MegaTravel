package org.xmlws.authenticationservice.repository;

import javax.xml.xquery.XQException;

import org.springframework.stereotype.Repository;
import org.xmlws.authenticationservice.model.User;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;

@Repository
public class UserRepository extends ExistXQJRepository<User> {

	public UserRepository() throws XQException {
		super(new XMLEntityInformation<User>(User.class));
	}
}