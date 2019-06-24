package org.xmlws.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {

    private Long id;
    private AccommodationDto accommodation;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double price;
    private Boolean realized;
    private ReservationRating reservationRating;
    private List<Message> message;
}
