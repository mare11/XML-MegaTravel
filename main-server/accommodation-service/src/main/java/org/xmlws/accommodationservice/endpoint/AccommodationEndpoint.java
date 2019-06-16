package org.xmlws.accommodationservice.endpoint;

import java.util.List;

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
import org.xmlws.accommodationservice.gen.GetAccommodationTypesRequest;
import org.xmlws.accommodationservice.gen.GetAccommodationTypesResponse;
import org.xmlws.accommodationservice.gen.GetAdditionalServicesRequest;
import org.xmlws.accommodationservice.gen.GetAdditionalServicesResponse;
import org.xmlws.accommodationservice.gen.UpdateAccommodationRequest;
import org.xmlws.accommodationservice.gen.UpdateAccommodationResponse;
import org.xmlws.accommodationservice.model.AccommodationType;
import org.xmlws.accommodationservice.model.AdditionalService;
import org.xmlws.accommodationservice.service.AccommodationService;
import org.xmlws.accommodationservice.service.AccommodationTypeService;
import org.xmlws.accommodationservice.service.AdditionalServiceService;

@Endpoint
public class AccommodationEndpoint {

	@Autowired
	private AccommodationService accommodationService;
	
	@Autowired
	private AdditionalServiceService additionalServiceService;
	
	@Autowired
	private AccommodationTypeService accommodationTypeService;

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
		AccommodationDTO accommodationDTO = accommodationService.update(request.getAccommodationDTO());
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
	
	@PayloadRoot(localPart = "getAccommodationTypesRequest")
	@ResponsePayload
	public GetAccommodationTypesResponse getAccommodationTypes(@RequestPayload GetAccommodationTypesRequest request){
		GetAccommodationTypesResponse response = new GetAccommodationTypesResponse();
		List<AccommodationType> accommodationTypes = accommodationTypeService.findAll();
		response.getAccommodationType().addAll(accommodationTypes);
		return response;
	}
	
	@PayloadRoot(localPart = "getAdditionalServicesRequest")
	@ResponsePayload
	public GetAdditionalServicesResponse getAdditionalServices(@RequestPayload GetAdditionalServicesRequest request){
		GetAdditionalServicesResponse response = new GetAdditionalServicesResponse();
		List<AdditionalService> additionalServices = additionalServiceService.findAll();
		response.getAdditionalService().addAll(additionalServices);
		return response;
	}
}
