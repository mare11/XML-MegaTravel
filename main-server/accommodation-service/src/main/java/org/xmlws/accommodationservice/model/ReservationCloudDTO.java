package org.xmlws.accommodationservice.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCloudDTO {

	private Long id;
	private Long accommodationId;
	private String username;
	private Integer rating;
	private String comment;
	private Boolean published;
	private LocalDateTime timestamp;
}
