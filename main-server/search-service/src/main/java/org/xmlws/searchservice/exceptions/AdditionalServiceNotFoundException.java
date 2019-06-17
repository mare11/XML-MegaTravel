package org.xmlws.searchservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AdditionalServiceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5913858900393676796L;
	
	public AdditionalServiceNotFoundException(Long id) {
		super("Additional service with id '" + id + "' is not found!");
	}
}