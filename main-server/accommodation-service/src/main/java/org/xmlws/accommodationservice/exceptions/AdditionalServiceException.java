package org.xmlws.accommodationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AdditionalServiceException extends RuntimeException {
	
    public AdditionalServiceException(String serviceName) {
        super("Additional service '" + serviceName + "' already exist!"); 
    }
    
    public AdditionalServiceException() {
    	super("Additional service can't be deleted!");
    }
}
