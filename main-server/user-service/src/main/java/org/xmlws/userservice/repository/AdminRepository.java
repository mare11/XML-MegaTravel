package org.xmlws.userservice.repository;

import org.springframework.stereotype.Repository;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;
import org.xmlws.userservice.model.Administrator;

@Repository
public class AdminRepository extends ExistXQJRepository<Administrator> {

    public AdminRepository() {
        super(new XMLEntityInformation<Administrator>(Administrator.class));
    }
}
