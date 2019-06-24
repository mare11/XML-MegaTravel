package org.xmlws.reservationservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xmlws.reservationservice.model.Message;
import org.xmlws.reservationservice.model.Reservation;
import org.xmlws.reservationservice.model.ReservationCloudDTO;
import org.xmlws.reservationservice.model.ReservationDto;
import org.xmlws.reservationservice.service.ReservationService;

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

    @RequestMapping(method = RequestMethod.GET, value = "{id}/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReservationDto>> getUserReservations(@PathVariable Long id, @PathVariable String username) {
        List<ReservationDto> reservations = reservationService.findReservationsByUser(id, username);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/accommodations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reservation>> getAccommodationReservations(@PathVariable Long id) {
        List<Reservation> reservations = reservationService.findReservationsByAccommodation(id);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> addMessage(@PathVariable Long id, @RequestBody Message message) {
        message = reservationService.addMessage(id, message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/rating", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRating(@RequestBody ReservationCloudDTO reservationDTO) {
        return reservationService.addRating(reservationDTO);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> publishComment(@PathVariable Long id) {
       return reservationService.publishComment(id);
    }
}
