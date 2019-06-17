package org.xmlws.searchservice.dto;

import java.time.LocalDate;
import java.util.List;

import org.xmlws.searchservice.model.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationSearchDto {
	
	private Location location;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer numberOfPersons;
	private List<String> accommodationTypes;
	private List<Integer> categories;
	private List<String> additionalServices;
	private Double distanceFromLocation;
}