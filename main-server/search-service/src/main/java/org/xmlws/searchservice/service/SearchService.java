package org.xmlws.searchservice.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.xmlws.searchservice.dto.AccommodationResultDto;
import org.xmlws.searchservice.dto.AccommodationSearchDto;
import org.xmlws.searchservice.exceptions.AccommodationTypeNotFoundException;
import org.xmlws.searchservice.exceptions.AdditionalServiceNotFoundException;
import org.xmlws.searchservice.exceptions.LocationNotFoundException;
import org.xmlws.searchservice.exceptions.RequiredSearchParametersException;
import org.xmlws.searchservice.model.Accommodation;
import org.xmlws.searchservice.model.AccommodationType;
import org.xmlws.searchservice.model.AdditionalService;
import org.xmlws.searchservice.model.Location;
import org.xmlws.searchservice.model.Reservation;
import org.xmlws.searchservice.repository.AccommodationRepository;
import org.xmlws.searchservice.repository.AccommodationTypeRepository;
import org.xmlws.searchservice.repository.AdditionalServiceRepository;
import org.xmlws.searchservice.repository.LocationRepository;
import org.xmlws.searchservice.util.AccommodationComparator;

@Service
public class SearchService {

	@Autowired
	private AccommodationRepository accommodationRepository;
	
	@Autowired
	private AccommodationTypeRepository accommodationTypeRepository;
	
	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;
	
	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private WebClient.Builder webClientBuilder;	
	
	private Map<Long, AccommodationType> accommodationsTypes = new HashMap<>();
	private Map<Long, AdditionalService> additionalServices = new HashMap<>();
	private Map<Long, Location> locations = new HashMap<>();
	
	private DecimalFormat twoPlacesFormat = new DecimalFormat("#.##");
	private AccommodationComparator accommodationComparator = new AccommodationComparator();
	
	public List<AccommodationResultDto> searchForAccommodations(AccommodationSearchDto searchDto) {
		//Check required fields
		boolean anyRequiredFieldIsNull = Stream.of(searchDto.getLocation(), searchDto.getStartDate(),searchDto.getEndDate(), searchDto.getNumberOfPersons())
											   .anyMatch(Objects::isNull);
		if (anyRequiredFieldIsNull) {
			throw new RequiredSearchParametersException();
		}
		
		//Initial search
		List<Accommodation> accommodations = this.findAllByNumOfPersonsAndCategoryAndTypeAndAdditionalServices(searchDto.getNumberOfPersons(),
																											   searchDto.getCategories(),
																											   searchDto.getAccommodationTypes(),
																											   searchDto.getAdditionalServices());
		//Find unavailable accommodations
		List<Accommodation> unavailableAccommodations = this.findAllUnavailableAccommodationsByPeriod(searchDto.getStartDate(), 
																									  searchDto.getEndDate());
		
		//Discard unavailable accommodations from earlier found accommodations
		List<Long> unavailableAccommodationIds = unavailableAccommodations.stream()
																		  .map(Accommodation::getId)
																		  .collect(Collectors.toList());
		accommodations = accommodations.stream()
				                       .filter(accomodation -> !unavailableAccommodationIds.contains(accomodation.getId()))
				                       .collect(Collectors.toList());
				
		//Discard accommodations with one or more reservation that overlaps with the requested period from earlier found accommodations 
		accommodations = this.findNotReservedAccommodationsInGivenPeriod(accommodations, searchDto.getStartDate(), searchDto.getEndDate());

		//Convert search results in suitable DTOs
		List<AccommodationResultDto> resultDtos = new ArrayList<>();
		for (Accommodation accommodation : accommodations) {
			AccommodationResultDto resultDto = modelMapper.map(accommodation, AccommodationResultDto.class);
			resultDto.setAccommodationType(this.findOneAccommodationTypeById(accommodation.getAccommodationTypeId()));
			resultDto.setAdditionalServices(this.findAllAdditionalServicesByIds(accommodation.getAdditionalServiceIds()));
			resultDto.setLocation(this.findOneLocationById(accommodation.getLocationId()));
			resultDtos.add(resultDto);
		}
		
		return resultDtos;
	}
	
	private List<Accommodation> findAllByNumOfPersonsAndCategoryAndTypeAndAdditionalServices(int numberOfPersons, 
																		List<Integer> categories,
																		List<String> accommodationTypes,
																		List<String> additionalServices) {	
		
		StringBuilder searchXPath = new StringBuilder("[");
		
		//search by number of persons
		searchXPath.append("numberOfPersons = '" + numberOfPersons + "'");
		
		//search by categories
		searchXPath.append(this.generateSearchForAnyInListFilter(categories, "category", false));
		
		//search by accommodation types
		this.accommodationsTypes = this.findAllAccommodationTypesByName(accommodationTypes);
		searchXPath.append(this.generateSearchForAnyInListFilter(new ArrayList<>(this.accommodationsTypes.keySet()), "accommodationTypeId", false));
		
		//search by additional services
		this.additionalServices = this.findAllAdditionalServicesByName(additionalServices);
		if (!this.additionalServices.isEmpty()) {
			for(Long additionalServiceId : new ArrayList<>(this.additionalServices.keySet())) {
				searchXPath.append(this.generateSearchForAnyInListFilter(Arrays.asList(additionalServiceId), "additionalServiceIds", false));
			}			
		}
		
		searchXPath.append("]");
		
		return this.accommodationRepository.findWithFilter(searchXPath.toString());
	}
	
	public AccommodationType findOneAccommodationTypeById(Long accommodationTypeId) {
		if (this.accommodationsTypes.containsKey(accommodationTypeId)) {
			return this.accommodationsTypes.get(accommodationTypeId);
		}
		try {
			AccommodationType accommodationType = this.accommodationTypeRepository.findOne(accommodationTypeId.toString());
			this.accommodationsTypes.put(accommodationTypeId, accommodationType);
			return accommodationType;
		} catch (Exception e) {
			throw new AccommodationTypeNotFoundException(accommodationTypeId);
		}
	}
	
	public List<AdditionalService> findAllAdditionalServicesByIds(List<Long> additionalServiceIds) {
		List<AdditionalService> additionalServices = new ArrayList<AdditionalService>();
		for (Long additionalServiceId : additionalServiceIds) {
			if (this.additionalServices.containsKey(additionalServiceId)) {
				additionalServices.add(this.additionalServices.get(additionalServiceId));
				continue;
			}
			try {
				AdditionalService additionalService = this.additionalServiceRepository.findOne(additionalServiceId.toString());
				this.additionalServices.put(additionalServiceId, additionalService);
				additionalServices.add(additionalService);
			} catch (Exception e) {
				throw new AdditionalServiceNotFoundException(additionalServiceId);
			}			
		}
		
		return additionalServices;
	}
	
	public Location findOneLocationById(Long locationId) {
		if (this.locations.containsKey(locationId)) {
			return this.locations.get(locationId);
		}
		
		Location location;
		try {
			location = this.locationRepository.findOne(locationId.toString());
			this.locations.put(locationId, location);
			return location;
		} catch (Exception e) {
			throw new LocationNotFoundException(locationId);
		}
	}
	
	private Map<Long, AccommodationType> findAllAccommodationTypesByName(List<String> accommodationTypeNames) {
		String filter = this.generateSearchForAnyInListFilter(accommodationTypeNames, "typeName", true);
		List<AccommodationType> accommodationTypes;
		if (filter.isEmpty()) {
			accommodationTypes = this.accommodationTypeRepository.findAll();
		} else {
			accommodationTypes = this.accommodationTypeRepository.findWithFilter("[" + filter + "]");
		}
		
		return accommodationTypes.stream().collect(Collectors.toMap(AccommodationType::getId, Function.identity()));
	}
	
	private Map<Long, AdditionalService> findAllAdditionalServicesByName(List<String> additionalServiceNames) {
		String filter = this.generateSearchForAnyInListFilter(additionalServiceNames, "additionalServiceName", true);
		List<AdditionalService> additionalServices;
		if (filter.isEmpty()) {
			return new HashMap<>();
		} else {
			additionalServices = this.additionalServiceRepository.findWithFilter("[" + filter + "]");
		}
		
		return additionalServices.stream().collect(Collectors.toMap(AdditionalService::getId, Function.identity()));
	}
	
	private String generateSearchForAnyInListFilter(List<?> objects, String elementName, boolean firstParameter) {
		if (objects == null || objects.isEmpty()) {
			return "";
		}
		
		StringBuilder searchXPath = new StringBuilder();
		if (!firstParameter) {
			searchXPath.append(" and ");
		}
		
		searchXPath.append(elementName + " = (");
		IntStream.range(0, objects.size() - 1).forEach(i -> searchXPath.append("'" + objects.get(i) + "', "));
		searchXPath.append("'" + objects.get(objects.size() - 1) + "')");
		
		return searchXPath.toString();
	}
	
	/**
	 * Method for gathering unavailable accommodations in the given period. <br>
	 * Example filter: [Unavailability[ <br> (startDate<=xs:date('2019-10-03') and endDate>=xs:date('2019-10-03')) or <br>
	 * 								   (startDate<=xs:date('2019-10-10') and endDate>=xs:date('2019-10-10')) or <br>
	 * 								   (startDate>=xs:date('2019-10-03') and endDate<=xs:date('2019-10-10'))] <br> ] <br>
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<Accommodation> findAllUnavailableAccommodationsByPeriod(LocalDate startDate, LocalDate endDate) {
		List<Accommodation> unavailableAccommodations = new ArrayList<>();
		
		if (startDate != null && endDate != null) {
			String filter = "[Unavailability[(startDate<=xs:date('" + startDate.toString() + "') and"
										 	+ " endDate>=xs:date('" + startDate.toString() + "')) or"
										 + " (startDate<=xs:date('" + endDate.toString() + "') and"
									 		+ " endDate>=xs:date('" + endDate.toString() + "')) or"
										 + " (startDate>=xs:date('" + startDate.toString() + "') and"
									 		+ " endDate<=xs:date('" + endDate.toString()  + "'))]]";
			
			unavailableAccommodations = this.accommodationRepository.findWithFilter(filter);
		}
		return unavailableAccommodations;
	}
	
	private List<Accommodation> findNotReservedAccommodationsInGivenPeriod(List<Accommodation> accommodations, LocalDate startDate, LocalDate endDate) {
		
		return accommodations.stream().filter(accommodation -> {
			List<Reservation> reservations = webClientBuilder.build()
														     .get()
														     .uri("http://reservation-service/reservations/accommodations/" + accommodation.getId())
														     .retrieve()
														     .bodyToMono(new ParameterizedTypeReference<List<Reservation>>() {})
														     .block();
	
			if (reservations != null && !reservations.isEmpty()) {
				return reservations.stream().allMatch(reservation -> {
					if ((reservation.getStartDate().isBefore(startDate) && reservation.getEndDate().isBefore(startDate)) ||
						(reservation.getStartDate().isAfter(endDate) && reservation.getEndDate().isAfter(endDate))) {
						
						return true;
					} else {
						return false;
					}
				});
			} else {
				return true;
			}
		}).collect(Collectors.toList());
	}
	
	public List<AccommodationResultDto> calculateDistances(List<AccommodationResultDto> resultDtos, Location searchLocation) {
		resultDtos.stream().forEach(result -> result.setDistance(this.calculateDisance(result.getLocation(), searchLocation)));
		return resultDtos;
	}
	
	private double calculateDisance(Location resultLocation, Location searchLocaiton) {
		if (resultLocation == null || searchLocaiton == null) {
			return 0.0;
		}
		
		double resultLatitude = resultLocation.getLatitude().doubleValue();
		double resultLongitude = resultLocation.getLongitude().doubleValue();
		double searchLatitude = searchLocaiton.getLatitude().doubleValue();
		double searchLongitude = searchLocaiton.getLongitude().doubleValue();
		double degreeConstant = Math.PI / 180;
		
		double calculation = 0.5 - Math.cos((searchLatitude - resultLatitude) * degreeConstant) / 2
							+ Math.cos(resultLatitude * degreeConstant) * Math.cos(searchLatitude * degreeConstant)
							* (1 - Math.cos((searchLongitude - resultLongitude) * degreeConstant)) / 2;
		calculation = 12742 * Math.asin(Math.sqrt(calculation)); // 2 * R; R = 6371 km		
		return new BigDecimal(twoPlacesFormat.format(calculation)).doubleValue();
	}
	
	public List<AccommodationResultDto> sortByDistance(List<AccommodationResultDto> resultDtos) {
		return (resultDtos == null) ? null : resultDtos.stream().sorted(accommodationComparator).collect(Collectors.toList());
	}
	
	public List<AccommodationResultDto> discardTooFarAccommodations(List<AccommodationResultDto> resultDtos, Double distanceFromLocation) {
		return (resultDtos == null || distanceFromLocation == null) ? null : resultDtos.stream()
																					    .filter(result -> result.getDistance() <= distanceFromLocation)
																					    .collect(Collectors.toList());
	}
}