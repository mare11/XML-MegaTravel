package org.xmlws.authenticationservice.controller;

import java.io.IOException;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xmlws.authenticationservice.security.AuthenticationRequest;
import org.xmlws.authenticationservice.security.TokenUtility;
import org.xmlws.authenticationservice.security.UserState;

@RestController
public class AuthenticationController {
	
	@Autowired
	private TokenUtility tokenUtility;

	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserState> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) 
			throws AuthenticationException, IOException {
		
		UsernamePasswordAuthenticationToken authenticationToken 
											= new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
																					  authenticationRequest.getPassword());
		
		//Try to authenticate user
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//Extract username and authorities and generate token if user is authenticated successfully
		String username = authentication.getName();
		List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		String token = tokenUtility.generateToken(username, authorities);
		
		//Return token, username and authorities to client side
		UserState userState = new UserState(token, username, authorities);
		return new ResponseEntity<UserState>(userState, HttpStatus.OK);
	}
}