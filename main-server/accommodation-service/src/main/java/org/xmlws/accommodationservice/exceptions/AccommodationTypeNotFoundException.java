package org.xmlws.accommodationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccommodationTypeNotFoundException extends RuntimeException {
	
	public AccommodationTypeNotFoundException(Long id) {
		super("Accommodation type with id '" + id + "' does not exist!");
	}
}
