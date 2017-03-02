package com.jonasasx.roox.test.exceptions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;
import org.springframework.http.HttpStatus;

/**
 * Main class of Api exceptions
 * <p>
 * Created by ionas on 02.03.17.
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
abstract public class ResourceException extends Exception {
	public ResourceException(String s) {
		super(s);
	}

	@JsonGetter
	@Override
	public String getMessage() {
		return super.getMessage();
	}

	/**
	 * Http status
	 *
	 * @return Http status
	 */
	abstract public HttpStatus getStatus();
}
