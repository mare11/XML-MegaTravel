package org.xmlws.searchservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TooLongPeriodException extends RuntimeException {

	private static final long serialVersionUID = 5191458700208926163L;

	public TooLongPeriodException(Long days) {
		super("Period of " + days + " days is too long! \n"
			+ "Reservations for more than 30 days are not possible.");
	}
}