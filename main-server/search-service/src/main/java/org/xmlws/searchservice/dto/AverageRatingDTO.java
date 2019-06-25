package org.xmlws.searchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AverageRatingDTO {

	private Long accommodationId;
	private Double averageRating;
}
