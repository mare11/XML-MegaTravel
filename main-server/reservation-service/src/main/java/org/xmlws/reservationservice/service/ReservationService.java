package org.xmlws.reservationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.reservationservice.model.Message;
import org.xmlws.reservationservice.model.Reservation;
import org.xmlws.reservationservice.model.ReservationRating;
import org.xmlws.reservationservice.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    public Reservation createReservation(Reservation reservation) {

//        ADD CALL TO USER SERVICE

        reservation.setId(catalogRepository.getCatalogId(reservationRepository.getRootElementName()));
        return reservationRepository.save(reservation);
    }

    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findOne(id.toString());

//        ADD CALL TO ACCOMMODATION SERVICE FOR CANCEL CHECK,
//        AND USER SERVICE FOR DELETING RESERVATIONS IF CANCELING IS POSSIBLE
    }

    public List<Reservation> findReservationsByUser(Long userId) {
        return reservationRepository.findWithFilter("[userId = '" + userId + "']");
    }

    public List<Reservation> findReservationsByAccommodation(Long accommodationId) {
        return reservationRepository.findWithFilter("[accommodationId = '" + accommodationId + "']");
    }

    public Reservation addMessage(Long id, Message message) {
        Reservation reservation = reservationRepository.findOne(id.toString());
        reservation.getMessage().add(message);
        return reservationRepository.save(reservation);
    }

    public Reservation addRating(Long id, ReservationRating reservationRating) {
        Reservation reservation = reservationRepository.findOne(id.toString());
        reservation.setReservationRating(reservationRating);
        return reservationRepository.save(reservation);
    }

    public Reservation publishComment(Long id) {
        Reservation reservation = reservationRepository.findOne(id.toString());
        reservation.getReservationRating().setPublished(true);
        return reservationRepository.save(reservation);
    }
}
