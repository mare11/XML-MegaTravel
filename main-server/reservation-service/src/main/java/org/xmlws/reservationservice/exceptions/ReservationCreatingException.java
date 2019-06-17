package org.xmlws.reservationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ReservationCreatingException extends RuntimeException {

    public ReservationCreatingException() {
        super("Error while creating reservation!");
    }
}
