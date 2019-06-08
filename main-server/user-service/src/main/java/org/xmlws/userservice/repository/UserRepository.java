package org.xmlws.userservice.repository;

import org.springframework.stereotype.Repository;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;
import org.xmlws.userservice.model.User;

@Repository
public class UserRepository extends ExistXQJRepository<User> {

    public UserRepository() {
        super(new XMLEntityInformation<User>(User.class));
    }
}
