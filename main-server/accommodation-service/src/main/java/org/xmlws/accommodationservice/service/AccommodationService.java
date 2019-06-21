package org.xmlws.accommodationservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlws.accommodationservice.exceptions.AccommodationNotFoundException;
import org.xmlws.accommodationservice.gen.AccommodationDTO;
import org.xmlws.accommodationservice.model.Accommodation;
import org.xmlws.accommodationservice.model.AdditionalService;
import org.xmlws.accommodationservice.model.Location;
import org.xmlws.accommodationservice.model.ReservationCancelling;
import org.xmlws.accommodationservice.repository.AccommodationRepository;
import org.xmlws.dataservice.catalog.CatalogRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public AccommodationDTO findOne(Long id) {
        Accommodation accommodation = getAccommodation(id);
        AccommodationDTO accommodationDTO = mapper.map(accommodation, AccommodationDTO.class);
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
                Location savedLocation = locationService.save(accommodationDTO.getLocation());
                accommodation.setLocationId(savedLocation.getId());
            } else {
                accommodation.setLocationId(location.getId());
            }
        }
        accommodation = accommodationRepository.save(accommodation);
        accommodationDTO.setId(accommodation.getId());
        return accommodationDTO;
    }

    public AccommodationDTO update(AccommodationDTO accommodationDTO) {
        Accommodation accommodation = getAccommodation(accommodationDTO.getId());
        accommodation = mapper.map(accommodationDTO, Accommodation.class);
        accommodation.setAccommodationTypeId(accommodationDTO.getAccommodationType().getId());
        accommodation.getAdditionalServiceIds().clear();
        for (AdditionalService additionalService : accommodationDTO.getAdditionalService()) {
            accommodation.getAdditionalServiceIds().add(additionalService.getId());
        }
        accommodation.setLocationId(accommodationDTO.getLocation().getId());
        accommodation = accommodationRepository.save(accommodation);
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
}
