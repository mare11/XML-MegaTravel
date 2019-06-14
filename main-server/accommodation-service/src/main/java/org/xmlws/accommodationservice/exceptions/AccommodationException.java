package org.xmlws.accommodationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccommodationException extends RuntimeException {
	
	    public AccommodationException(Long id) {
	        super("Accommodation with id '" + id + "' does not exist!"); 
	    }
}
