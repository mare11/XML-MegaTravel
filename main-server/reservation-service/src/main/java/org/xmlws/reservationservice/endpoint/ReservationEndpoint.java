package org.xmlws.reservationservice.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.reservationservice.gen.GetAccReservationRequest;
import org.xmlws.reservationservice.gen.GetReservationResponse;
import org.xmlws.reservationservice.gen.GetUserReservationRequest;
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

    @PayloadRoot(localPart = "getUserReservationRequest")
    @ResponsePayload
    public GetReservationResponse getReservationsByUser(@RequestPayload GetUserReservationRequest userReservationRequest) {


//        dummy example
//        ReservationRating reservationRating = new ReservationRating(2, "top comment", LocalDateTime.now(), true);
//        Message message1 = new Message("ima li parkinga negdje blizu?!", LocalDateTime.now(), DirectionEnum.USER_TO_AGENT);
//        Message message2 = new Message("NEMA NIGDJE!", LocalDateTime.now(), DirectionEnum.AGENT_TO_USER);
//        Reservation reservation = new Reservation(2L, 1L,
//                LocalDate.now(), LocalDate.now(), false, reservationRating, Arrays.asList(message1, message2));
//        reservation.setId(catalogRepository.getCatalogId(reservationRepository.getRootElementName()));
//
//        reservationRepository.save(reservation);
//

        GetReservationResponse response = new GetReservationResponse();
        reservationService.findReservationsByUser(userReservationRequest.getUserId())
                .stream().forEach(res -> response.getReservation().add(res));

        return response;
    }

    @PayloadRoot(localPart = "getAccReservationRequest")
    @ResponsePayload
    public GetReservationResponse getReservationsByAccommodation(@RequestPayload GetAccReservationRequest accReservationRequest) {

        GetReservationResponse response = new GetReservationResponse();
        reservationService.findReservationsByAccommodation(accReservationRequest.getAccommodationId())
                .stream().forEach(res -> response.getReservation().add(res));

        return response;
    }

}
