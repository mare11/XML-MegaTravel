package org.xmlws.reservationservice.repository;

import org.springframework.stereotype.Repository;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;
import org.xmlws.reservationservice.model.Reservation;

@Repository
public class ReservationRepository extends ExistXQJRepository<Reservation> {

    public ReservationRepository() {
        super(new XMLEntityInformation<Reservation>(Reservation.class));
    }
}
