package org.xmlws.accommodationservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xmlws.accommodationservice.model.AdditionalService;
import org.xmlws.accommodationservice.service.AdditionalServiceService;

@RestController
@RequestMapping(value = "/additionalService")
public class AdditionalServiceController {

	@Autowired
	private AdditionalServiceService additionalServiceService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AdditionalService>> getAdditionalServices() {
		List<AdditionalService> additionalServices = additionalServiceService.findAll();
		return new ResponseEntity<>(additionalServices, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdditionalService> addAdditionalService(@RequestBody AdditionalService additionalService) {
		AdditionalService newAdditionalService = additionalServiceService.save(additionalService);
		return new ResponseEntity<>(newAdditionalService, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteAdditionalService(@RequestBody AdditionalService additionalService) {
		additionalServiceService.delete(additionalService.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
