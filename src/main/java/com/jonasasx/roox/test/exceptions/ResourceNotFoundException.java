package com.jonasasx.roox.test.exceptions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;
import org.springframework.http.HttpStatus;

/**
 * Resource not found Exception
 * <p>
 * Created by ionas on 02.03.17.
 */
public class ResourceNotFoundException extends ResourceException {

	public ResourceNotFoundException() {
		super("Resource not found");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HttpStatus getStatus() {
		return HttpStatus.NOT_FOUND;
	}
}
