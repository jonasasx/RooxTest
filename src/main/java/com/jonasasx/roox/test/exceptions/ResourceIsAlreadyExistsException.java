package com.jonasasx.roox.test.exceptions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;
import org.springframework.http.HttpStatus;

/**
 * Resource is already exists Exception
 * <p>
 * Created by ionas on 02.03.17.
 */
public class ResourceIsAlreadyExistsException extends ResourceException {

	public ResourceIsAlreadyExistsException() {
		super("Resource is already exists");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HttpStatus getStatus() {
		return HttpStatus.CONFLICT;
	}
}
