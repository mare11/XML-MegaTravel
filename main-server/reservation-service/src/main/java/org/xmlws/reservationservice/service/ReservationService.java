package org.xmlws.reservationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.reservationservice.exceptions.ReservationCancelingException;
import org.xmlws.reservationservice.exceptions.ReservationCreatingException;
import org.xmlws.reservationservice.exceptions.ReservationNotFoundException;
import org.xmlws.reservationservice.model.*;
import org.xmlws.reservationservice.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Reservation createReservation(Reservation reservation) {
        reservation.setRealized(false);
        reservation.setId(catalogRepository.getCatalogId(reservationRepository.getRootElementName()));

//      CALL TO USER SERVICE
        ClientResponse userClientResponse = webClientBuilder.build()
                .put()
                .uri("http://user-service/users/" + reservation.getUserId() + "/reservations/" + reservation.getId())
                .exchange()
                .block();

        if (userClientResponse.statusCode().equals(HttpStatus.OK)) {
//      CALL TO ACCOMMODATION SERVICE
            ClientResponse accClientResponse = webClientBuilder.build()
                    .put()
                    .uri("http://accommodation-service/accommodations/" + reservation.getAccommodationId() + "/reservations/" + reservation.getId())
                    .exchange()
                    .block();

            if (accClientResponse.statusCode().equals(HttpStatus.OK)) {
                return reservationRepository.save(reservation);
            }

        }

        throw new ReservationCreatingException();
    }

    public void cancelReservation(Long id) {
        Reservation reservation = getReservation(id);

        ReservationCancelling reservationCancelling = new ReservationCancelling(reservation.getAccommodationId(), reservation.getId(), reservation.getStartDate(), false);
//      CALL TO ACCOMMODATION SERVICE FOR CANCELLING CHECK
        reservationCancelling = webClientBuilder.build()
                .post()
                .uri("http://accommodation-service/accommodations/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(reservationCancelling))
                .retrieve()
                .bodyToMono(ReservationCancelling.class)
                .doOnError(throwable -> {
                    throw new ReservationCancelingException();
                })
                .block();

        if (reservationCancelling.getAllowedCancellation()) {
//      CALL TO USER SERVICE
            ClientResponse clientResponse = webClientBuilder.build()
                    .delete()
                    .uri("http://user-service/users/" + reservation.getUserId() + "/reservations/" + reservation.getId())
                    .exchange()
                    .block();

            if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                reservationRepository.delete(reservation);
                return;
            }
        }

        throw new ReservationCancelingException();
    }

    public List<ReservationDto> findReservationsByUser(Long userId) {
        List<Reservation> reservations = reservationRepository.findWithFilter("[userId = '" + userId + "']");
        return reservations.stream().map(reservation -> {
                    AccommodationDto accommodationDto = webClientBuilder.build()
                            .get()
                            .uri("http://accommodation-service/accommodations/" + reservation.getAccommodationId())
                            .retrieve()
                            .bodyToMono(AccommodationDto.class)
                            .doOnError(throwable -> {
                                throw new ReservationNotFoundException();
                            })
                            .block();
                    return new ReservationDto(reservation.getId(), accommodationDto, reservation.getStartDate(), reservation.getEndDate(), reservation.getPrice().doubleValue());
                }
        ).collect(Collectors.toList());
    }

    public List<Reservation> findReservationsByAccommodation(Long accommodationId) {
        return reservationRepository.findWithFilter("[accommodationId = '" + accommodationId + "']");
    }

    public Message addMessage(Long id, Message message) {
        Reservation reservation = getReservation(id);
        message.setTimestamp(LocalDateTime.now());
        reservation.getMessage().add(message);
        reservationRepository.save(reservation);
        return message;
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

    public Reservation setRealized(Long id) {
        Reservation reservation = getReservation(id);
        reservation.setRealized(true);
        return reservationRepository.save(reservation);
    }

    private Reservation getReservation(Long id) {
        List<Reservation> reservations = reservationRepository.findWithFilter("[id = '" + id + "']");
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException();
        }
        return reservations.get(0);
    }
}
