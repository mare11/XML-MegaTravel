package org.xmlws.reservationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.reservationservice.exceptions.ReservationNotFoundException;
import org.xmlws.reservationservice.model.Message;
import org.xmlws.reservationservice.model.Reservation;
import org.xmlws.reservationservice.model.ReservationCancelling;
import org.xmlws.reservationservice.model.ReservationRating;
import org.xmlws.reservationservice.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Reservation createReservation(Reservation reservation) {
        reservation.setId(catalogRepository.getCatalogId(reservationRepository.getRootElementName()));

//      CALL TO USER SERVICE
        ResponseEntity userResponseEntity = webClientBuilder.build()
                .put()
                .uri("http://user-service/users/" + reservation.getUserId() + "/reservations/" + reservation.getId())
                .retrieve()
                .bodyToMono(ResponseEntity.class)
                .block();

        if (userResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
//      CALL TO ACCOMMODATION SERVICE
            ResponseEntity accResponseEntity = webClientBuilder.build()
                    .put()
                    .uri("http://accommodation-service/accommodations/" + reservation.getAccommodationId() + "/reservations/" + reservation.getId())
                    .retrieve()
                    .bodyToMono(ResponseEntity.class)
                    .block();

            if (accResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
                return reservationRepository.save(reservation);
            }

        }

        throw new ReservationNotFoundException();
    }

    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findOne(id.toString());

        ReservationCancelling reservationCancelling = new ReservationCancelling(reservation.getAccommodationId(), reservation.getId(), reservation.getStartDate(), false);
//      CALL TO ACCOMMODATION SERVICE FOR CANCELLING CHECK
        reservationCancelling = webClientBuilder.build()
                .post()
                .uri("http://accommodation-service/accommodations/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(reservationCancelling))
                .retrieve()
                .bodyToMono(ReservationCancelling.class)
                .block();

        if (reservationCancelling.getAllowedCancellation()) {
//      CALL TO USER SERVICE
            ResponseEntity responseEntity = webClientBuilder.build()
                    .delete()
                    .uri("http://user-service/users/" + reservation.getUserId() + "/reservations/" + reservation.getId())
                    .retrieve()
                    .bodyToMono(ResponseEntity.class)
                    .block();

            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                reservationRepository.delete(reservation);
            }
        }
    }

    public List<Reservation> findReservationsByUser(Long userId) {
        return reservationRepository.findWithFilter("[userId = '" + userId + "']");
    }

    public List<Reservation> findReservationsByAccommodation(Long accommodationId) {
        return reservationRepository.findWithFilter("[accommodationId = '" + accommodationId + "']");
    }

    public Reservation addMessage(Long id, Message message) {
        Reservation reservation = getReservation(id);
        reservation.getMessage().add(message);
        return reservationRepository.save(reservation);
    }

    public Reservation addRating(Long id, ReservationRating reservationRating) {
        Reservation reservation = getReservation(id);
        reservation.setReservationRating(reservationRating);
        return reservationRepository.save(reservation);
    }

    public Reservation publishComment(Long id) {
        Reservation reservation = getReservation(id);
        reservation.getReservationRating().setPublished(true);
        return reservationRepository.save(reservation);
    }

    private Reservation getReservation(Long id) {
        Reservation reservation = reservationRepository.findOne(id.toString());
        if (reservation == null) {
            throw new ReservationNotFoundException();
        }
        return reservation;
    }
}
