package org.xmlws.reservationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ReservationCancelingException extends RuntimeException {

    public ReservationCancelingException() {
        super("Reservation can not be canceled!");
    }
}
