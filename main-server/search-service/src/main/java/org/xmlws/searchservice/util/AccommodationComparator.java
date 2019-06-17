package org.xmlws.searchservice.util;

import java.util.Comparator;

import org.xmlws.searchservice.dto.AccommodationResultDto;

public class AccommodationComparator implements Comparator<AccommodationResultDto> {

	@Override
	public int compare(AccommodationResultDto o1, AccommodationResultDto o2) {
		return o1.compareTo(o2);
	}
}