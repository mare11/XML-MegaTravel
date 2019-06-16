package org.xmlws.accommodationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AdditionalServiceNotFoundException extends RuntimeException {

	public AdditionalServiceNotFoundException(Long id) {
		super("Additional service with id '" + id + "' does not exist!");
	}
}
