package com.jonasasx.roox.test.controllers;

import com.jonasasx.roox.test.exceptions.ResourceException;
import com.jonasasx.roox.test.exceptions.ResourceIsAlreadyExistsException;
import com.jonasasx.roox.test.exceptions.ResourceNotFoundException;
import com.jonasasx.roox.test.services.CustomerService;
import com.jonasasx.roox.test.services.PartnerMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main class of RESTful Api
 * <p>
 * Created by ionas on 01.03.17.
 */
@RestController
@PreAuthorize("#cid == null || #cid == principal.id || #cid == '@me'")
public class ApiController {

	@Autowired
	protected CustomerService customerService;

	@Autowired
	protected PartnerMappingService partnerMappingService;

	/**
	 * Handle {@link ResourceNotFoundException}
	 *
	 * @param e exception
	 * @return response
	 */
	@ExceptionHandler({ResourceNotFoundException.class, ResourceIsAlreadyExistsException.class})
	public ResponseEntity<ResourceException> resourceNotFoundExceptionHandler(ResourceException e) {
		return new ResponseEntity<>(e, e.getStatus());
	}

}
