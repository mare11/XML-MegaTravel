package org.xmlws.searchservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RequiredSearchParametersException extends RuntimeException {

	private static final long serialVersionUID = 1084501934273482672L;

	public RequiredSearchParametersException() {
		super("Not filled all required fields! \n "
			+ "Required fields are: Location, Number of persons, Date of arrival and Date of departure.");
	}
}