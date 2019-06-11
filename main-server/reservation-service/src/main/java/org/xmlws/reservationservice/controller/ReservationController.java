package org.xmlws.reservationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmlws.reservationservice.model.Message;
import org.xmlws.reservationservice.model.Reservation;
import org.xmlws.reservationservice.model.ReservationRating;
import org.xmlws.reservationservice.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping(value = "/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        reservation = reservationService.createReservation(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reservation>> getUserReservations(@PathVariable Long id) {
        List<Reservation> reservations = reservationService.findReservationsByUser(id);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> addMessage(@PathVariable Long id, @RequestBody Message message) {
        Reservation reservation = reservationService.addMessage(id, message);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/rating", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> addRating(@PathVariable Long id, @RequestBody ReservationRating reservationRating) {
        Reservation reservation = reservationService.addRating(id, reservationRating);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> publishComment(@PathVariable Long id) {
        Reservation reservation = reservationService.publishComment(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }
}
