package org.xmlws.accommodationservice.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.xmlws.accommodationservice.gen.AccommodationDTO;
import org.xmlws.accommodationservice.gen.AddAccommodationRequest;
import org.xmlws.accommodationservice.gen.AddAccommodationResponse;
import org.xmlws.accommodationservice.gen.DeleteAccommodationRequest;
import org.xmlws.accommodationservice.gen.DeleteAccommodationResponse;
import org.xmlws.accommodationservice.gen.GetAccommodationRequest;
import org.xmlws.accommodationservice.gen.GetAccommodationResponse;
import org.xmlws.accommodationservice.gen.UpdateAccommodationRequest;
import org.xmlws.accommodationservice.gen.UpdateAccommodationResponse;
import org.xmlws.accommodationservice.service.AccommodationService;

@Endpoint
public class AccommodationEndpoint {

	@Autowired
	private AccommodationService accommodationService;

	@PayloadRoot(localPart = "getAccommodationRequest")
	@ResponsePayload
	public GetAccommodationResponse getAccommodation(@RequestPayload GetAccommodationRequest request) {
		GetAccommodationResponse response = new GetAccommodationResponse();
		AccommodationDTO accommodationDTO = accommodationService.findOne(request.getId());
		response.setAccommodationDTO(accommodationDTO);
		return response;
	}

	@PayloadRoot(localPart = "addAccommodationRequest")
	@ResponsePayload
	public AddAccommodationResponse addAccommodation(@RequestPayload AddAccommodationRequest request) {
		AddAccommodationResponse response = new AddAccommodationResponse();
		AccommodationDTO accommodationDTO = accommodationService.save(request.getAccommodationDTO());
		response.setAccommodationDTO(accommodationDTO);
		return response;
	}

	@PayloadRoot(localPart = "updateAccommodationRequest")
	@ResponsePayload
	public UpdateAccommodationResponse updateAccommodation(@RequestPayload UpdateAccommodationRequest request) {
		UpdateAccommodationResponse response = new UpdateAccommodationResponse();
		AccommodationDTO accommodationDTO = null;
		accommodationDTO = accommodationService.update(request.getAccommodationDTO());
		response.setAccommodationDTO(accommodationDTO);
		return response;
	}

	@PayloadRoot(localPart = "deleteAccommodationRequest")
	@ResponsePayload
	public DeleteAccommodationResponse getCountry(@RequestPayload DeleteAccommodationRequest request) {
		DeleteAccommodationResponse response = new DeleteAccommodationResponse();
		Boolean isDeleted = accommodationService.delete(request.getId());
		response.setFlag(isDeleted);
		return response;
	}
}
