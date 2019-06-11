package org.xmlws.reservationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlws.reservationservice.model.Reservation;
import org.xmlws.reservationservice.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> findReservationsByUser(Long userId) {
        return reservationRepository.findWithFilter("[userId = '" + userId + "']");
    }

    public List<Reservation> findReservationsByAccommodation(Long accommodationId) {
        return reservationRepository.findWithFilter("[accommodationId = '" + accommodationId + "']");
    }
}
