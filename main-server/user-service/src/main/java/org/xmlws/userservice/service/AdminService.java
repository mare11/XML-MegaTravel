package org.xmlws.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.userservice.model.Administrator;
import org.xmlws.userservice.model.Authority;
import org.xmlws.userservice.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    public Administrator addAdmin(Administrator administrator) {
        administrator.setId(catalogRepository.getCatalogId(adminRepository.getRootElementName()));
        administrator.getAuthority().add(new Authority("ROLE_ADMIN"));
        return adminRepository.save(administrator);
    }

    public void deleteAdmin(Administrator administrator) {
        adminRepository.delete(administrator);
    }
}
