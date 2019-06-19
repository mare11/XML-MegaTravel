package org.xmlws.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.userservice.exceptions.UserAlreadyExistsException;
import org.xmlws.userservice.model.Administrator;
import org.xmlws.userservice.model.Authority;
import org.xmlws.userservice.model.AuthorityEnum;
import org.xmlws.userservice.repository.AdminRepository;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<Administrator> findAllAdmins() {
        List<Administrator> admins = adminRepository.findAll();
        return admins;
    }

    public Administrator addAdmin(Administrator administrator) {

//        CALL TO AUTH SERVICE FOR UNIQUE USERNAME CHECK
        ClientResponse clientResponse = webClientBuilder.build()
                .get()
                .uri("http://authentication-service/authentication/username/" + administrator.getUsername())
                .exchange()
                .block();

        if (clientResponse.statusCode().equals(HttpStatus.OK)) {
            administrator.setId(catalogRepository.getCatalogId(adminRepository.getRootElementName()));
            administrator.getAuthority().add(new Authority(AuthorityEnum.ROLE_ADMIN));
            return adminRepository.save(administrator);
        }

        throw new UserAlreadyExistsException(administrator.getUsername());
    }

    public void deleteAdmin(Administrator administrator) {
        adminRepository.delete(administrator);
    }
}