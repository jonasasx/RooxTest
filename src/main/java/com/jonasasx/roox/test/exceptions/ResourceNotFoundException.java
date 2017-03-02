package com.jonasasx.roox.test.exceptions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;

/**
 * Created by ionas on 02.03.17.
 */
public class ResourceNotFoundException extends ResourceException {

	public ResourceNotFoundException() {
		super("Resource not found");
	}

}
