package org.xmlws.registrationservice.service;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.registrationservice.exceptions.TokenExpiredException;
import org.xmlws.registrationservice.exceptions.UserAlreadyEnabledException;
import org.xmlws.registrationservice.exceptions.UserAlreadyExistsException;
import org.xmlws.registrationservice.exceptions.UserNotFoundException;
import org.xmlws.registrationservice.model.Authority;
import org.xmlws.registrationservice.model.AuthorityEnum;
import org.xmlws.registrationservice.model.User;
import org.xmlws.registrationservice.repository.UserRepository;

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
	
	public User save(User user, HttpServletRequest request){

		ClientResponse usernameClientResponse = webClientBuilder.build()
												                .get()
												                .uri("http://authentication-service/authentication/username/" + user.getUsername())
												                .exchange()
												                .block();
												               
		if (usernameClientResponse.statusCode().equals(HttpStatus.OK)){	
			 ClientResponse emailClientResponse = webClientBuilder.build()
											                      .get()
											                      .uri("http://authentication-service/authentication/email/" + user.getEmail())
											                      .exchange()
											                      .block();
			 
			 if (emailClientResponse.statusCode().equals(HttpStatus.OK)){
				 String hashedPassword = webClientBuilder.build()
										                 .get()
										                 .uri("http://authentication-service/authentication/password/" + user.getPassword())
										                 .exchange()
										                 .block()
										                 .bodyToMono(String.class)
										                 .block();
				 user.setPassword(hashedPassword);
				// Set user's authority
				Authority userAuthority = new Authority();
				userAuthority.setRole(AuthorityEnum.ROLE_USER);
				user.getAuthority().add(userAuthority);
				user.setEnabled(false);
				user.setDeleted(false);
				Long id = catalogRepository.getCatalogId(userRepository.getRootElementName());
				user.setId(id);
				user = userRepository.save(user);
				// Send email to user with activation link
				try {
					this.emailService.sendRegistrationNotificaition(user, request);
				} catch (MailException | InterruptedException | MessagingException e) {
				}
				return user;
			}else{
				throw new UserAlreadyExistsException(user.getUsername(),user.getEmail());
			}
		}else{
			throw new UserAlreadyExistsException(user.getUsername());
		}
	}
	
	public User verifyUser(String token){
		String username = webClientBuilder.build()
										  .get()
										  .uri("http://authentication-service/authentication/username/token/" + token)
										  .exchange()
										  .block()
										  .bodyToMono(String.class)
										  .block();
		
		List<User> users = userRepository.findWithFilter("[username='" + username + "']");
		if (users != null && !users.isEmpty()) {
			User user = users.get(0);
			Date expiresIn = webClientBuilder.build()
										     .get()
										     .uri("http://authentication-service/authentication/expirationdate/token/" + token)
										     .exchange()
										     .block()
										     .bodyToMono(Date.class)
										     .block();
			if (user.isEnabled()) {
				throw new UserAlreadyEnabledException(username);
			} else if (expiresIn.getTime() <= new Date().getTime()) {
				throw new TokenExpiredException();
			} else {
				user.setEnabled(true);
				user = userRepository.save(user);
				return user;
			}
		} else {
			throw new UserNotFoundException(username);
		}
	}
}
