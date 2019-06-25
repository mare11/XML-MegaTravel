package org.xmlws.accommodationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDto {

	private Long id;
    private AccommodationType accommodationType;
    private Integer category;
    private Boolean freeCancellation;
    private Integer cancellationDays;
    private Integer numberOfPersons;
    private Location location;
}
