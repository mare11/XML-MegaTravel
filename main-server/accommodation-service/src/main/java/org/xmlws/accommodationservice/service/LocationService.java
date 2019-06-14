package org.xmlws.accommodationservice.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.xmlws.accommodationservice.exceptions.LocationNotFoundException;
import org.xmlws.accommodationservice.model.Location;
import org.xmlws.accommodationservice.repository.LocationRepository;
import org.xmlws.dataservice.catalog.CatalogRepository;

@Service
public class LocationService {

	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private CatalogRepository catalogRepository;
	
	public Location findOne(Long id){
		return getLocation(id);
	}
	
	public List<Location> findAll() {
		return locationRepository.findAll();
	}
	
	public Location save(Location location) {
		Long id = catalogRepository.getCatalogId(locationRepository.getRootElementName());
		location.setId(id);
		return locationRepository.save(location);
	}
	
	public void delete(Long id) throws NotFoundException{
		Location location = getLocation(id);
		if(location == null){
			throw new NotFoundException();
		}else{
			locationRepository.delete(location);
		}
	}
	
	public Location findOneByLatitudeAndLongitude(BigDecimal latitude,BigDecimal longitude){
		List<Location> locations = locationRepository.findWithFilter("[latitude='" + latitude + "' and longitude='" + longitude + "']");
		if (locations.isEmpty()){
			return null;
		}else{
			return locations.get(0);
		}
	}
	
	public Location getLocation(Long id){
		List<Location> locations = locationRepository.findWithFilter("[id='" + id + "']");
		if (locations.isEmpty()) {
            throw new LocationNotFoundException(id);
        }
        return locations.get(0);
	}
}
