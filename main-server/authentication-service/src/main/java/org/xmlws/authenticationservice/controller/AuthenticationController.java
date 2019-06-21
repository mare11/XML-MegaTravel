package org.xmlws.authenticationservice.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xmlws.authenticationservice.exceptions.UserAlreadyExistsException;
import org.xmlws.authenticationservice.exceptions.UsernameNullPointerException;
import org.xmlws.authenticationservice.exceptions.WrongAuthorityException;
import org.xmlws.authenticationservice.model.UserEntity;
import org.xmlws.authenticationservice.security.AuthenticationRequest;
import org.xmlws.authenticationservice.security.TokenUtility;
import org.xmlws.authenticationservice.security.UserState;
import org.xmlws.authenticationservice.service.UserService;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenUtility tokenUtility;

	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping(value = "/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserState validateToken(@PathVariable String token) 
			throws AuthenticationException, Exception {
		
		String username = tokenUtility.getUsernameFromToken(token);
		UserEntity user = (UserEntity) this.userService.loadUserByUsername(username);
		
		if (user == null) {
			return null;
		}
		
		List<String> authorities = tokenUtility.getAuthoritiesFromToken(token);
		if (!authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
				.containsAll(user.getAuthorities().stream().collect(Collectors.toList()))) {
			return null;
		}
		
		return new UserState(token, username, user.getId());
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserState> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) 
			throws AuthenticationException, IOException {

		UsernamePasswordAuthenticationToken authenticationToken 
											= new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
																					  authenticationRequest.getPassword());
		
		//Try to authenticate user
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		
		if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(authenticationRequest.getAuthority().name()))) {
			throw new WrongAuthorityException(authenticationRequest.getUsername(), authenticationRequest.getAuthority());
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//Extract username and authorities and generate token if user is authenticated successfully
		String username = authentication.getName();
		List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		String token = tokenUtility.generateToken(username, authorities);
		
		//Return token, username and authorities to client side
		UserState userState = new UserState(token, username, ((UserEntity)authentication.getPrincipal()).getId());
		return new ResponseEntity<UserState>(userState, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/token/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String generateToken(@PathVariable String username) {
		
		return this.tokenUtility.generateToken(username, this.userService.loadUserByUsername(username)
																		 .getAuthorities()
																		 .stream()
																		 .map(GrantedAuthority::getAuthority)
																		 .collect(Collectors.toList()));
	}
	
	@RequestMapping(value = "/password/{password}", method = RequestMethod.GET)
	public String hashPassword(@PathVariable String password) {
		
		return this.userService.generatePassword(password);
	}
	
	@RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
	public ResponseEntity<?> checkUsername(@PathVariable String username) throws UsernameNullPointerException {
		try {
			this.userService.loadUserByUsername(username);
			throw new UserAlreadyExistsException(username);
		} catch (UsernameNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
	public ResponseEntity<?> checkEmail(@PathVariable String email) throws AuthenticationException {
		if (this.userService.checkEmail(email)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value = "/username/token/{token}", method = RequestMethod.GET)
	public String getUsernameFromToken(@PathVariable String token) throws Exception {
		return this.tokenUtility.getUsernameFromToken(token);
	}
	
	@RequestMapping(value = "/expirationdate/token/{token}", method = RequestMethod.GET)
	public Date getExpirationDateFromToken(@PathVariable String token) throws Exception {
		return this.tokenUtility.getExpirationDateFromToken(token);
	}
}