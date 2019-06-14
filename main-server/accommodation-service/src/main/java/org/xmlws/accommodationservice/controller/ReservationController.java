package org.xmlws.accommodationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xmlws.accommodationservice.model.ReservationCancelling;
import org.xmlws.accommodationservice.service.AccommodationService;

@RestController
@RequestMapping(value = "/accommodations")
public class ReservationController {
	
	@Autowired
	private AccommodationService accommodationService;

	@RequestMapping(method = RequestMethod.PUT, value = "/{accommodationId}/reservations/{reservationId}")
    public ResponseEntity<?> addReservation(@PathVariable Long accommodationId, @PathVariable Long reservationId) {
        accommodationService.addReservation(accommodationId, reservationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationCancelling> cancelReservation(@RequestBody ReservationCancelling reservationCancelling) {
        reservationCancelling = accommodationService.cancelReservation(reservationCancelling);
        return new ResponseEntity<>(reservationCancelling, HttpStatus.OK);
    }
}
