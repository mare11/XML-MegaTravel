package org.xmlws.registrationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("Username '" + username + "' already in use!");
    }

    public UserAlreadyExistsException(String username, String email) {
        super("Username '" + username + "' or email '" + email + "' already in use!");
    }
}
