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

    public Reservation findOne() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.get(0);
    }
}
