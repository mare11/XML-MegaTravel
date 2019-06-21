package org.xmlws.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.userservice.exceptions.UserAlreadyExistsException;
import org.xmlws.userservice.model.Administrator;
import org.xmlws.userservice.model.Authority;
import org.xmlws.userservice.model.AuthorityEnum;
import org.xmlws.userservice.model.UserUniquenessDto;
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

//        CALL TO AUTH SERVICE FOR UNIQUE USERNAME CHECK AND PASSWORD HASHING
        UserUniquenessDto userUniquenessDto = new UserUniquenessDto(administrator.getUsername(), administrator.getPassword(), null);
        userUniquenessDto = webClientBuilder.build()
                .post()
                .uri("http://authentication-service/authentication/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(userUniquenessDto))
                .retrieve()
                .bodyToMono(UserUniquenessDto.class)
                .doOnError(throwable -> {
                    throw new UserAlreadyExistsException(administrator.getUsername());
                })
                .block();

        administrator.setId(catalogRepository.getCatalogId(adminRepository.getRootElementName()));
        administrator.setPassword(userUniquenessDto.getPassword());
        administrator.getAuthority().add(new Authority(AuthorityEnum.ROLE_ADMIN));
        return adminRepository.save(administrator);

    }

    public void deleteAdmin(Administrator administrator) {
        adminRepository.delete(administrator);
    }
}