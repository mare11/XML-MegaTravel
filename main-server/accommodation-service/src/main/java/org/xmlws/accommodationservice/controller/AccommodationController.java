package org.xmlws.accommodationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmlws.accommodationservice.gen.AccommodationDTO;
import org.xmlws.accommodationservice.model.AccommodationDto;
import org.xmlws.accommodationservice.model.AverageRatingDTO;
import org.xmlws.accommodationservice.model.ReservationCancelling;
import org.xmlws.accommodationservice.model.ReservationCloudDTO;
import org.xmlws.accommodationservice.service.AccommodationService;

import java.util.List;

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

    @RequestMapping(method = RequestMethod.GET, value = "/details/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> getAccommodationDetails(@PathVariable Long accommodationId) {
        AccommodationDTO accommodationDTO = accommodationService.findOne(accommodationId);
        return new ResponseEntity<>(accommodationDTO, HttpStatus.OK);
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

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/reviews/published", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReservationCloudDTO>> getPublishedReviews(@PathVariable Long id) {
        List<ReservationCloudDTO> response = accommodationService.getPublishedReviews(id);
        return new ResponseEntity<List<ReservationCloudDTO>>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/reviews/unpublished", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReservationCloudDTO>> getUnpublishedReviews() {
        List<ReservationCloudDTO> response = accommodationService.getUnpublishedReviews();
        return new ResponseEntity<List<ReservationCloudDTO>>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/reviews/average", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AverageRatingDTO>> getAverageRatings() {
        List<AverageRatingDTO> response = accommodationService.getAverageRatings();
        return new ResponseEntity<List<AverageRatingDTO>>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/reviews/average/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AverageRatingDTO> getAverageRating(@PathVariable Long id) {
        AverageRatingDTO response = accommodationService.getAverageRating(id);
        return new ResponseEntity<AverageRatingDTO>(response, HttpStatus.OK);
    }
}
