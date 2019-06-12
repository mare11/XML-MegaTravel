package org.xmlws.reservationservice.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.xmlws.reservationservice.gen.GetAccReservationRequest;
import org.xmlws.reservationservice.gen.GetReservationResponse;
import org.xmlws.reservationservice.gen.GetUserReservationRequest;
import org.xmlws.reservationservice.service.ReservationService;

@Endpoint
public class ReservationEndpoint {

    @Autowired
    private ReservationService reservationService;

    @PayloadRoot(localPart = "getUserReservationRequest")
    @ResponsePayload
    public GetReservationResponse getReservationsByUser(@RequestPayload GetUserReservationRequest userReservationRequest) {

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
