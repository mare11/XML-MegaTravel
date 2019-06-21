package org.xmlws.registrationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.registrationservice.exceptions.TokenExpiredException;
import org.xmlws.registrationservice.exceptions.UserAlreadyEnabledException;
import org.xmlws.registrationservice.exceptions.UserAlreadyExistsException;
import org.xmlws.registrationservice.exceptions.UserNotFoundException;
import org.xmlws.registrationservice.model.*;
import org.xmlws.registrationservice.repository.UserRepository;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CatalogRepository catalogRepository;

    public User save(User user, HttpServletRequest request) {

//        CALL TO AUTH SERVICE FOR UNIQUE USERNAME AND EMAIL CHECK AND PASSWORD HASHING
        UserUniquenessDto userUniquenessDto = new UserUniquenessDto(user.getUsername(), user.getPassword(), user.getEmail());
        userUniquenessDto = webClientBuilder.build()
                .post()
                .uri("http://authentication-service/authentication/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(userUniquenessDto))
                .retrieve()
                .bodyToMono(UserUniquenessDto.class)
                .doOnError(throwable -> {
                    throw new UserAlreadyExistsException(user.getUsername(), user.getEmail());
                })
                .block();

        user.setPassword(userUniquenessDto.getPassword());
        // Set user's authority
        Authority userAuthority = new Authority();
        userAuthority.setRole(AuthorityEnum.ROLE_USER);
        user.getAuthority().add(userAuthority);
        user.setEnabled(false);
        user.setDeleted(false);
        Long id = catalogRepository.getCatalogId(userRepository.getRootElementName());
        user.setId(id);
        userRepository.save(user);
        // Send email to user with activation link
        try {
            this.emailService.sendRegistrationNotificaition(user, request);
        } catch (MailException | InterruptedException | MessagingException e) {
        }
        return user;
    }

    public User verifyUser(String token) {
        TokenDataDto tokenDataDto = webClientBuilder.build()
                .get()
                .uri("http://authentication-service/authentication/token/data/" + token)
                .retrieve()
                .bodyToMono(TokenDataDto.class)
                .doOnError(throwable -> {
                    throw new TokenExpiredException();
                })
                .block();

        List<User> users = userRepository.findWithFilter("[username='" + tokenDataDto.getUsername() + "']");
        if (users != null && !users.isEmpty()) {
            User user = users.get(0);
            if (user.isEnabled()) {
                throw new UserAlreadyEnabledException(tokenDataDto.getUsername());
            } else if (tokenDataDto.getExpirationDate().getTime() <= new Date().getTime()) {
                throw new TokenExpiredException();
            } else {
                user.setEnabled(true);
                return userRepository.save(user);
            }
        } else {
            throw new UserNotFoundException(tokenDataDto.getUsername());
        }
    }
}
