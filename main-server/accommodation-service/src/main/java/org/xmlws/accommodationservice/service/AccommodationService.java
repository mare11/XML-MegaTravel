package org.xmlws.accommodationservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.xmlws.accommodationservice.exceptions.AccommodationNotFoundException;
import org.xmlws.accommodationservice.exceptions.AverageRatingException;
import org.xmlws.accommodationservice.gen.AccommodationDTO;
import org.xmlws.accommodationservice.model.*;
import org.xmlws.accommodationservice.repository.AccommodationRepository;
import org.xmlws.dataservice.catalog.CatalogRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccommodationService {

    private ModelMapper mapper = new ModelMapper();

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private AccommodationTypeService accommodationTypeService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RestTemplate restTemplate;

    public AccommodationDTO findOne(Long id) {
        Accommodation accommodation = getAccommodation(id);
        return transformAccommodation(accommodation);
    }
    
    public List<AccommodationDTO> findAccommodations(Long agentId){
    	List<Accommodation> accommodations = accommodationRepository.findWithFilter("[agentId='" + agentId + "']");
    	List<AccommodationDTO> accommodationsDTOs = new ArrayList<>();
    	for (Accommodation accommodation : accommodations){
			AccommodationDTO accommodationDTO = transformAccommodation(accommodation);
			accommodationsDTOs.add(accommodationDTO);
    	}
    	return accommodationsDTOs;
    }

    public AccommodationDto findById(Long accommodationId) {
        Accommodation accommodation = getAccommodation(accommodationId);
        AccommodationDto accommodationDto = mapper.map(accommodation, AccommodationDto.class);

        accommodationDto.setAccommodationType(accommodationTypeService.findOne(accommodation.getAccommodationTypeId()));
        accommodationDto.setLocation(locationService.findOne(accommodation.getLocationId()));

        return accommodationDto;
    }

    public AccommodationDTO save(AccommodationDTO accommodationDTO) {
        Long id = catalogRepository.getCatalogId(accommodationRepository.getRootElementName());
        Accommodation accommodation = mapper.map(accommodationDTO, Accommodation.class);
        accommodation.setId(id);
        if (accommodationDTO.getAccommodationType() != null) {
            accommodation.setAccommodationTypeId(accommodationDTO.getAccommodationType().getId());
        }
        if (accommodationDTO.getAdditionalService() != null && !accommodationDTO.getAdditionalService().isEmpty()) {
            for (AdditionalService additionalService : accommodationDTO.getAdditionalService()) {
                accommodation.getAdditionalServiceIds().add(additionalService.getId());
            }
        }
        if (accommodationDTO.getLocation() != null) {
            Location location = locationService.findOneByLatitudeAndLongitude(
                    accommodationDTO.getLocation().getLatitude(), accommodationDTO.getLocation().getLongitude());
            if (location == null) {
                location = locationService.save(accommodationDTO.getLocation());
            }
			accommodation.setLocationId(location.getId());
			accommodationDTO.setLocation(location);
        }
        accommodation = accommodationRepository.save(accommodation);
        accommodationDTO.setId(accommodation.getId());
        return accommodationDTO;
    }

    public AccommodationDTO update(AccommodationDTO accommodationDTO) {
        Accommodation accommodation = mapper.map(accommodationDTO, Accommodation.class);
        accommodation.setAccommodationTypeId(accommodationDTO.getAccommodationType().getId());
        accommodation.getAdditionalServiceIds().clear();
        for (AdditionalService additionalService : accommodationDTO.getAdditionalService()) {
            accommodation.getAdditionalServiceIds().add(additionalService.getId());
        }
        accommodation.setLocationId(accommodationDTO.getLocation().getId());
		accommodationDTO.getPeriodPrice().stream().forEach(periodPrice ->
                accommodation.getPeriodPrice().add(periodPrice));
        accommodationDTO.getUnavailability().stream().forEach(unavailability ->
                accommodation.getUnavailability().add(unavailability));
        accommodationRepository.save(accommodation);
        return accommodationDTO;
    }

    public Boolean delete(Long id) {
        Accommodation accommodation = getAccommodation(id);
        accommodationRepository.delete(accommodation);
        return true;
    }

    public void addReservation(Long accommodationId, Long reservationId) {
        Accommodation accommodation = getAccommodation(accommodationId);
        accommodation.getReservationIds().add(reservationId);
        accommodationRepository.save(accommodation);
    }

    public ReservationCancelling cancelReservation(ReservationCancelling reservationCancelling) {
        Accommodation accommodation = accommodationRepository
                .findOne(reservationCancelling.getAccommodationId().toString());
        if (accommodation.isFreeCancellation()) {
            if (accommodation.getCancellationDays() < ChronoUnit.DAYS.between(LocalDate.now(),
                    reservationCancelling.getReservationStartDate())) {
                reservationCancelling.setAllowedCancellation(true);
                accommodation.getReservationIds().remove(reservationCancelling.getReservationId());
                accommodationRepository.save(accommodation);
            }
        }
        return reservationCancelling;
    }

    public Accommodation getAccommodation(Long id) {
        List<Accommodation> accommodations = accommodationRepository.findWithFilter("[id='" + id + "']");
        if (accommodations.isEmpty()) {
            throw new AccommodationNotFoundException(id);
        }
        return accommodations.get(0);
    }

    public List<ReservationCloudDTO> getPublishedReviews(Long id) {
        try {
            ResponseEntity<List<ReservationCloudDTO>> response = restTemplate.exchange("https://xml-megatravel.appspot.com/api/reviews/published/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<List<ReservationCloudDTO>>() {
            });
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new AccommodationNotFoundException(id);
        }
    }

    public List<ReservationCloudDTO> getUnpublishedReviews() {
        try {
            ResponseEntity<List<ReservationCloudDTO>> response = restTemplate.exchange("https://xml-megatravel.appspot.com/api/reviews/unpublished", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReservationCloudDTO>>() {
            });
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new AccommodationNotFoundException();
        }
    }

    public List<AverageRatingDTO> getAverageRatings() {
        try {
            ResponseEntity<List<AverageRatingDTO>> response = restTemplate.exchange("https://xml-megatravel.appspot.com/api/reviews/average", HttpMethod.GET, null, new ParameterizedTypeReference<List<AverageRatingDTO>>() {
            });
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new AverageRatingException();
        }
    }

    public AverageRatingDTO getAverageRating(Long id) {
        try {
            ResponseEntity<List<AverageRatingDTO>> response = restTemplate.exchange("https://xml-megatravel.appspot.com/api/reviews/average/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<List<AverageRatingDTO>>() {
            });
            if (response.getBody().isEmpty()) {
                return new AverageRatingDTO(id, 0.0);
            }
            return response.getBody().get(0);
        } catch (HttpClientErrorException e) {
            throw new AverageRatingException();
        }
    }
    
    private AccommodationDTO transformAccommodation(Accommodation accommodation){
    	AccommodationDTO accommodationDTO = mapper.map(accommodation, AccommodationDTO.class);
        accommodation.getPeriodPrice().stream().forEach(periodPrice ->
                accommodationDTO.getPeriodPrice().add(periodPrice));
        accommodation.getUnavailability().stream().forEach(unavailability ->
                accommodationDTO.getUnavailability().add(unavailability));

        if (accommodation.getAccommodationTypeId() != null) {
            accommodationDTO
                    .setAccommodationType(accommodationTypeService.findOne(accommodation.getAccommodationTypeId()));
        }
        if (accommodation.getAdditionalServiceIds() != null && !accommodation.getAdditionalServiceIds().isEmpty()) {
            for (Long additionalServiceId : accommodation.getAdditionalServiceIds()) {
                accommodationDTO.getAdditionalService().add(additionalServiceService.findOne(additionalServiceId));
            }
        }
        if (accommodation.getLocationId() != null) {
            accommodationDTO.setLocation(locationService.findOne(accommodation.getLocationId()));
        }
        return accommodationDTO;
    }
}
