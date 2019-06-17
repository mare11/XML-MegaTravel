package org.xmlws.searchservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DistanceTooFarException extends RuntimeException {

	private static final long serialVersionUID = 5191458700208926163L;

	public DistanceTooFarException(Double distance) {
		super(distance + "km is too far to search for! \n"
			+ "Distances greater than 100km is not allowed to search for.");
	}
}