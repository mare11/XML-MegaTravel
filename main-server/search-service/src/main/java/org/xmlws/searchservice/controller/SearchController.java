package org.xmlws.searchservice.controller;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xmlws.searchservice.dto.AccommodationResultDto;
import org.xmlws.searchservice.dto.AccommodationSearchDto;
import org.xmlws.searchservice.exceptions.DistanceTooFarException;
import org.xmlws.searchservice.exceptions.TooLongPeriodException;
import org.xmlws.searchservice.service.SearchService;

@RestController
@RequestMapping(value = "/search")
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	private static final long PERIOD_LIMIT = 30;
	private static final double DISTANCE_LIMIT = 100;
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AccommodationResultDto>> search(@RequestBody AccommodationSearchDto searchDto) {
		
		//Check period limit
		if (searchDto.getStartDate() != null && searchDto.getEndDate() != null) {
			long requestedPeriod = ChronoUnit.DAYS.between(searchDto.getStartDate(), searchDto.getEndDate().plusDays(1));
			if (requestedPeriod > PERIOD_LIMIT) {
				throw new TooLongPeriodException(requestedPeriod);
			}			
		}
		
		//Do search by entered parameters
		List<AccommodationResultDto> resultDtos = this.searchService.searchForAccommodations(searchDto);
		
		//Calculate distance for each accommodation
		resultDtos = this.searchService.calculateDistances(resultDtos, searchDto.getLocation());
		
		//Discard accommodations that are too far from entered distance
		Double distanceFromLocation = searchDto.getDistanceFromLocation();
		if (distanceFromLocation != null) {
			if (distanceFromLocation > DISTANCE_LIMIT) {
				throw new DistanceTooFarException(searchDto.getDistanceFromLocation());
			} else {
				resultDtos = this.searchService.discardTooFarAccommodations(resultDtos, distanceFromLocation);
			}
		}
		
		//Sort accommodations by distance
		resultDtos = this.searchService.sortByDistance(resultDtos);
		
		return new ResponseEntity<>(resultDtos, HttpStatus.OK);
	}
}