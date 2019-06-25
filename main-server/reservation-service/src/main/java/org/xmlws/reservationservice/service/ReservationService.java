package org.xmlws.reservationservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.reservationservice.exceptions.RatingAlreadyExistException;
import org.xmlws.reservationservice.exceptions.ReservationCancelingException;
import org.xmlws.reservationservice.exceptions.ReservationCreatingException;
import org.xmlws.reservationservice.exceptions.ReservationNotFoundException;
import org.xmlws.reservationservice.model.AccommodationDto;
import org.xmlws.reservationservice.model.Message;
import org.xmlws.reservationservice.model.Reservation;
import org.xmlws.reservationservice.model.ReservationCancelling;
import org.xmlws.reservationservice.model.ReservationCloudDTO;
import org.xmlws.reservationservice.model.ReservationDto;
import org.xmlws.reservationservice.model.ReservationRating;
import org.xmlws.reservationservice.repository.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;
    
    @Autowired
    private RestTemplate restTemplate;

    private ModelMapper mapper = new ModelMapper();

    public Reservation createReservation(Reservation reservation) {
        reservation.setRealized(false);
        reservation.setId(catalogRepository.getCatalogId(reservationRepository.getRootElementName()));

//      CALL TO USER SERVICE
        ClientResponse userClientResponse = webClientBuilder.build()
                .put()
                .uri("http://user-service/users/" + reservation.getUserId() + "/reservations/" + reservation.getId())
                .exchange()
                .block();

        if (userClientResponse.statusCode().equals(HttpStatus.OK)) {
//      CALL TO ACCOMMODATION SERVICE
            ClientResponse accClientResponse = webClientBuilder.build()
                    .put()
                    .uri("http://accommodation-service/accommodations/" + reservation.getAccommodationId() + "/reservations/" + reservation.getId())
                    .exchange()
                    .block();

            if (accClientResponse.statusCode().equals(HttpStatus.OK)) {
                return reservationRepository.save(reservation);
            }

        }

        throw new ReservationCreatingException();
    }

    public void cancelReservation(Long id) {
        Reservation reservation = getReservation(id);

        ReservationCancelling reservationCancelling = new ReservationCancelling(reservation.getAccommodationId(), reservation.getId(), reservation.getStartDate(), false);
//      CALL TO ACCOMMODATION SERVICE FOR CANCELLING CHECK
        reservationCancelling = webClientBuilder.build()
                .post()
                .uri("http://accommodation-service/accommodations/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(reservationCancelling))
                .retrieve()
                .bodyToMono(ReservationCancelling.class)
                .doOnError(throwable -> {
                    throw new ReservationCancelingException();
                })
                .block();

        if (reservationCancelling.getAllowedCancellation()) {
//      CALL TO USER SERVICE
            ClientResponse clientResponse = webClientBuilder.build()
                    .delete()
                    .uri("http://user-service/users/" + reservation.getUserId() + "/reservations/" + reservation.getId())
                    .exchange()
                    .block();

            if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                reservationRepository.delete(reservation);
                return;
            }
        }

        throw new ReservationCancelingException();
    }

    public List<ReservationDto> findReservationsByUser(Long userId, String username) {
    	ResponseEntity<List<ReservationCloudDTO>> response = restTemplate.exchange("https://xml-megatravel.appspot.com/api/reviews/user/"+ username, HttpMethod.GET , null, new ParameterizedTypeReference<List<ReservationCloudDTO>>(){});
        List<ReservationCloudDTO> ratings = response.getBody();
    	List<Reservation> reservations = reservationRepository.findWithFilter("[userId = '" + userId + "']");
        return reservations.stream().map(reservation -> {
                    AccommodationDto accommodationDto = webClientBuilder.build()
                            .get()
                            .uri("http://accommodation-service/accommodations/" + reservation.getAccommodationId())
                            .retrieve()
                            .bodyToMono(AccommodationDto.class)
                            .doOnError(throwable -> {
                                throw new ReservationNotFoundException();
                            })
                            .block();
                    ReservationDto reservationDto = mapper.map(reservation, ReservationDto.class);
                    for (ReservationCloudDTO rating: ratings){
                    	if (rating.getId().equals(reservationDto.getId())){
                    		reservationDto.setReservationRating(new ReservationRating(rating.getRating(), rating.getComment(), rating.getTimestamp(), rating.getPublished()));
                    		break;
                    	}
                    }
                    reservationDto.setAccommodation(accommodationDto);
                    return reservationDto;
                }
        ).collect(Collectors.toList());
    }

    public List<Reservation> findReservationsByAccommodation(Long accommodationId) {
        return reservationRepository.findWithFilter("[accommodationId = '" + accommodationId + "']");
    }

    public Message addMessage(Long id, Message message) {
        Reservation reservation = getReservation(id);
        message.setTimestamp(LocalDateTime.now());
        reservation.getMessage().add(message);
        reservationRepository.save(reservation);
        return message;
    }

    public ReservationCloudDTO addRating(ReservationCloudDTO reservationDTO) {
       	try{
       		HttpEntity<ReservationCloudDTO> request = new HttpEntity<>(reservationDTO);
       		return restTemplate.postForObject("https://xml-megatravel.appspot.com/api/reviews", request, ReservationCloudDTO.class);
    	}catch(HttpClientErrorException e){
    		throw new RatingAlreadyExistException();
    	}
    }

    public ResponseEntity<?> publishComment(Long id) {
        try{
        	return restTemplate.exchange("https://xml-megatravel.appspot.com/api/reviews/publish/"+ id, HttpMethod.PUT , null, Void.class);
        }catch(HttpClientErrorException e){
        	throw new ReservationNotFoundException();
        }
    }

    public Reservation setRealized(Long id) {
        Reservation reservation = getReservation(id);
        reservation.setRealized(true);
        return reservationRepository.save(reservation);
    }

    private Reservation getReservation(Long id) {
        List<Reservation> reservations = reservationRepository.findWithFilter("[id = '" + id + "']");
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException();
        }
        return reservations.get(0);
    }
}
