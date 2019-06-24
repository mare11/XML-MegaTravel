package org.xmlws.accommodationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AccommodationTypeException extends RuntimeException {
	
    public AccommodationTypeException(String typeName) {
        super("Accommodation type '" + typeName + "' already exist!"); 
    }
    
    public AccommodationTypeException(){
    	super("Accommodation type can't be deleted!");
    }
}
