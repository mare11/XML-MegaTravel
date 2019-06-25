package org.xmlws.searchservice.dto;

import java.util.List;

import org.xmlws.searchservice.model.AccommodationType;
import org.xmlws.searchservice.model.AdditionalService;
import org.xmlws.searchservice.model.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationResultDto implements Comparable<AccommodationResultDto> {
	
	private Long id;
    private AccommodationType accommodationType;
    private Integer category;
    private List<AdditionalService> additionalServices;
    private Boolean freeCancellation;
    private Integer cancellationDays;
    private String description;
    private List<String> images;
    private Integer numberOfPersons;
    private Location location;  
    private Double priceForRequestedPeriod;
    private Double distance;
    private Double averageRating;
    
    @Override
	public int compareTo(AccommodationResultDto o) {
		return (this.getDistance() - o.getDistance()) < 0 ? -1 : (this.getDistance() - o.getDistance()) > 0 ? 1 : 0;
	}
}