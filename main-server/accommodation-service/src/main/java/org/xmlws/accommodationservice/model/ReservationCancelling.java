package org.xmlws.accommodationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCancelling {

    private Long accommodationId;
    private Long reservationId;
    private LocalDate reservationStartDate;
    private Boolean allowedCancellation; 
}
