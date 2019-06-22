package org.xmlws.accommodationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmlws.accommodationservice.model.AccommodationDto;
import org.xmlws.accommodationservice.model.ReservationCancelling;
import org.xmlws.accommodationservice.service.AccommodationService;

@RestController
@RequestMapping(value = "/accommodations")
public class AccommodationController {

    @Autowired
    private AccommodationService accommodationService;

    @RequestMapping(method = RequestMethod.GET, value = "/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDto> getAccommodation(@PathVariable Long accommodationId) {
        AccommodationDto accommodationDto = accommodationService.findById(accommodationId);
        return new ResponseEntity<>(accommodationDto, HttpStatus.OK);
    }

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
