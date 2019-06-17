package org.xmlws.searchservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AccommodationTypeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8493443625478851189L;

	public AccommodationTypeNotFoundException(Long id) {
		super("Accommodation type with id '" + id + "' is not found!");
	}
}