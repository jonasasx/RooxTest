package com.jonasasx.roox.test;

import com.jonasasx.roox.test.entities.Customer;
import com.jonasasx.roox.test.entities.PartnerMapping;
import com.jonasasx.roox.test.exceptions.ResourceException;
import com.jonasasx.roox.test.exceptions.ResourceIsAlreadyExistsException;
import com.jonasasx.roox.test.exceptions.ResourceNotFoundException;
import com.jonasasx.roox.test.services.CustomerService;
import com.jonasasx.roox.test.services.PartnerMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by ionas on 01.03.17.
 */

@RestController
public class ApiController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PartnerMappingService partnerMappingService;

	@RequestMapping(method = RequestMethod.GET, value = "/customer/{cid}")
	@PreAuthorize("#cid == principal.id")
	public ResponseEntity<Customer> getCustomer(@PathVariable Long cid) throws ResourceNotFoundException {
		Customer customer = customerService.findById(cid);
		if (customer == null) {
			throw new ResourceNotFoundException();
		}
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/customer/{cid}/partnerMapping/{pid}")
	@PreAuthorize("#cid == principal.id")
	public ResponseEntity<PartnerMapping> getPartnerMapping(@PathVariable Long cid, @PathVariable Long pid) throws ResourceException {
		PartnerMapping partnerMapping = partnerMappingService.findPartnerMappingById(pid);
		if (partnerMapping == null || !cid.equals(partnerMapping.getCustomer().getId())) {
			throw new ResourceNotFoundException();
		}
		return new ResponseEntity<>(partnerMapping, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/customer/{cid}/partnerMapping")
	@PreAuthorize("#cid == principal.id")
	public ResponseEntity<?> createPartnerMapping(@RequestBody PartnerMapping partnerMapping,
	                                              @PathVariable Long cid, UriComponentsBuilder ucBuilder) throws ResourceException {
		partnerMapping.setId(null);
		if (partnerMappingService.exists(partnerMapping)) {
			throw new ResourceIsAlreadyExistsException();
		}
		partnerMapping.setCustomer(customerService.findById(cid));
		partnerMappingService.save(partnerMapping);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/customer/{cid}/partnerMapping/{pid}").buildAndExpand(cid, partnerMapping.getId()).toUri());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/customer/{cid}/partnerMapping/{pid}")
	@PreAuthorize("#cid == principal.id")
	public ResponseEntity<PartnerMapping> updatePartnerMapping(@RequestBody PartnerMapping postPartnerMapping,
	                                                           @PathVariable Long cid, @PathVariable Long pid) throws ResourceException {
		PartnerMapping partnerMapping = findPartnerMapping(cid, pid);
		partnerMapping.copyData(postPartnerMapping);
		partnerMappingService.save(partnerMapping);

		return new ResponseEntity<>(partnerMapping, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/customer/{cid}/partnerMapping/{pid}")
	@PreAuthorize("#cid == principal.id")
	public ResponseEntity<PartnerMapping> deletePartnerMapping(@PathVariable Long cid, @PathVariable Long pid) throws ResourceException {
		PartnerMapping partnerMapping = findPartnerMapping(cid, pid);
		partnerMappingService.delete(partnerMapping);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private PartnerMapping findPartnerMapping(Long cid, Long pid) throws ResourceNotFoundException {
		PartnerMapping partnerMapping = partnerMappingService.findPartnerMappingById(pid);
		if (partnerMapping == null || !cid.equals(partnerMapping.getCustomer().getId())) {
			throw new ResourceNotFoundException();
		}
		return partnerMapping;
	}

	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<ResourceException> resourceNotFoundExceptionHandler(ResourceException e) {
		return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ResourceIsAlreadyExistsException.class})
	public ResponseEntity<ResourceException> resourceIsAlreadyExistsExceptionHandler(ResourceException e) {
		return new ResponseEntity<>(e, HttpStatus.CONFLICT);
	}
}
