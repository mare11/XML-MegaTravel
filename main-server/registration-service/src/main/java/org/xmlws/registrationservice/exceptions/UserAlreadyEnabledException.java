package org.xmlws.registrationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyEnabledException extends RuntimeException {

	public UserAlreadyEnabledException(String username) {
		super("User with username '" + username + "' is already enabled!");
	}
}
