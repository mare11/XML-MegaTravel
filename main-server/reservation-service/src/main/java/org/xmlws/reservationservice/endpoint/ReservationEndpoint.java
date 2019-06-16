package org.xmlws.reservationservice.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.xmlws.reservationservice.gen.*;
import org.xmlws.reservationservice.model.Reservation;
import org.xmlws.reservationservice.service.ReservationService;

@Endpoint
public class ReservationEndpoint {

    @Autowired
    private ReservationService reservationService;

    @PayloadRoot(localPart = "getReservationRequest")
    @ResponsePayload
    public GetReservationResponse getReservationsByAccommodation(@RequestPayload GetReservationRequest accReservationRequest) {
        GetReservationResponse response = new GetReservationResponse();
        reservationService.findReservationsByAccommodation(accReservationRequest.getAccommodationId())
                .stream().forEach(res -> response.getReservation().add(res));

        return response;
    }

    @PayloadRoot(localPart = "addMessageRequest")
    @ResponsePayload
    public AddMessageResponse addMessage(@RequestPayload AddMessageRequest addMessageRequest) {
        AddMessageResponse response = new AddMessageResponse();
        Reservation reservation = reservationService.addMessage(addMessageRequest.getReservationId(), addMessageRequest.getMessage());
        response.setReservation(reservation);
        return response;
    }

    @PayloadRoot(localPart = "setRealizedRequest")
    @ResponsePayload
    public SetRealizedResponse setRealized(@RequestPayload SetRealizedRequest setRealizedRequest) {
        SetRealizedResponse response = new SetRealizedResponse();
        Reservation reservation = reservationService.setRealized(setRealizedRequest.getReservationId());
        response.setReservation(reservation);
        return response;
    }

}
