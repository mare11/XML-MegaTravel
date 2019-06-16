package org.xmlws.authenticationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UsernameNullPointerException extends AuthenticationException {

	private static final long serialVersionUID = -3636864448277418805L;

	public UsernameNullPointerException() {
		super("You have to specify username, it cannot be null!");
	}
}