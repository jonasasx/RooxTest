package com.jonasasx.roox.test.exceptions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;

/**
 * Created by ionas on 02.03.17.
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ResourceException extends Exception {
	public ResourceException(String s) {
		super(s);
	}

	@JsonGetter
	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
