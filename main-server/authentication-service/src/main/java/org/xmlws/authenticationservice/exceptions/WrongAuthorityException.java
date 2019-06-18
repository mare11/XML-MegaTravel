package org.xmlws.authenticationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.xmlws.authenticationservice.model.AuthorityEnum;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class WrongAuthorityException  extends AuthenticationException {

	private static final long serialVersionUID = 6018194950192960439L;

	public WrongAuthorityException(String username, AuthorityEnum authority) {
		super("User with username '" + username + "' has no following role: " + authority.name() + "!");
	}
}