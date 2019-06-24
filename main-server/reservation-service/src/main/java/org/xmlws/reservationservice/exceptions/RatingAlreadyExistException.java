package org.xmlws.reservationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RatingAlreadyExistException extends RuntimeException {

    public RatingAlreadyExistException() {
        super("Reservation rating already exist!");
    } 
}
