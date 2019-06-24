package org.xmlws.accommodationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) 
public class AverageRatingException extends RuntimeException {
	
    public AverageRatingException() {
        super("Error in calculating average ratings!"); 
    } 
}
