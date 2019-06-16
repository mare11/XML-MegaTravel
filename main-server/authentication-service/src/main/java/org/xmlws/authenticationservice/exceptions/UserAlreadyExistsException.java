package org.xmlws.authenticationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends AuthenticationException {
	
	private static final long serialVersionUID = 2345839021155163074L;

	public UserAlreadyExistsException(String username) {
		super("User with username '" + username + "' already exists!");
	}
}