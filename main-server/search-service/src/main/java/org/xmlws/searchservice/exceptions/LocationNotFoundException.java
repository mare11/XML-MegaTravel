package org.xmlws.searchservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class LocationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 521008523190177978L;
	
	public LocationNotFoundException(Long id) {
		super("Location with id '" + id + "' is not found!");
	}
}