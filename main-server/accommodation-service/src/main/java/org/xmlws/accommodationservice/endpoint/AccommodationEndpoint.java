package org.xmlws.accommodationservice.endpoint;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.xmlws.accommodationservice.gen.GetAccommodationRequest;
import org.xmlws.accommodationservice.gen.GetAccommodationResponse;
//import org.xmlws.accommodationservice.repository.AccommodationRepository;

@Endpoint
public class AccommodationEndpoint {
 
	private static final String NAMESPACE_URI = "http://www.xmlws.org/accommodationservice/gen";
	
//	@Autowired
//    private AccommodationRepository accommodationRepository;
 
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccommodationRequest")
    @ResponsePayload
    public GetAccommodationResponse getCountry(@RequestPayload GetAccommodationRequest request) {
        GetAccommodationResponse response = new GetAccommodationResponse();
        response.setAccommodation("RADI DZONIIII!!!");
        return response;
    }
}
