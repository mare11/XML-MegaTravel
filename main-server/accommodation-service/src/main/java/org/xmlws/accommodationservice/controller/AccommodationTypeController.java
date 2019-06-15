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
import org.xmlws.accommodationservice.model.AccommodationType;
import org.xmlws.accommodationservice.service.AccommodationTypeService;

@RestController
@RequestMapping(value = "/accommodationTypes")
public class AccommodationTypeController {

	@Autowired
	private AccommodationTypeService accommodationTypeService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AccommodationType>> getAccommodationTypes() {
		List<AccommodationType> accommodationTypes = accommodationTypeService.findAll();
		return new ResponseEntity<>(accommodationTypes, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AccommodationType> addAccomodationType(@RequestBody AccommodationType accommodationType) {
		AccommodationType newAccommodationType = accommodationTypeService.save(accommodationType);
		return new ResponseEntity<>(newAccommodationType, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteAccommodationType(@RequestBody AccommodationType accommodationType) {
		accommodationTypeService.delete(accommodationType.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
