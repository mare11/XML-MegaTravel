package org.xmlws.authenticationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailNullPointerException extends AuthenticationException {

	private static final long serialVersionUID = -2786691653132220643L;

	public EmailNullPointerException() {
		super("You have to specify email, it cannot be null!");
	}
}