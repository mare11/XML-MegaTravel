package org.xmlws.reservationservice.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.reservationservice.gen.GetReservationRequest;
import org.xmlws.reservationservice.gen.GetReservationResponse;
import org.xmlws.reservationservice.model.DirectionEnum;
import org.xmlws.reservationservice.model.Message;
import org.xmlws.reservationservice.model.Reservation;
import org.xmlws.reservationservice.model.ReservationRating;
import org.xmlws.reservationservice.repository.ReservationRepository;
import org.xmlws.reservationservice.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Endpoint
public class ReservationEndpoint {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @PayloadRoot(localPart = "getReservationRequest")
    @ResponsePayload
    public GetReservationResponse getReservation(@RequestPayload GetReservationRequest request) {


//        dummy example
        ReservationRating reservationRating = new ReservationRating(5, "top comment", LocalDateTime.now(), true);
        Message message1 = new Message("koja je cijena za septembar??? pozzz", LocalDateTime.now(), DirectionEnum.USER_TO_AGENT);
        Message message2 = new Message("neprocjenjivooo poydrav", LocalDateTime.now(), DirectionEnum.AGENT_TO_USER);
        Reservation reservation = new Reservation(1L, 1L,
                LocalDate.now(), LocalDate.now(), false, reservationRating, Arrays.asList(message1, message2));
        reservation.setId(catalogRepository.getCatalogId(reservationRepository.getRootElementName()));

        reservationRepository.save(reservation);
//

        GetReservationResponse response = new GetReservationResponse();
        response.setReservation(reservationService.findOne());

        return response;
    }

}
