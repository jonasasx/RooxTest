package com.jonasasx.roox.test.controllers;

import com.jonasasx.roox.test.entities.PartnerMapping;
import com.jonasasx.roox.test.exceptions.ResourceException;
import com.jonasasx.roox.test.exceptions.ResourceIsAlreadyExistsException;
import com.jonasasx.roox.test.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by ionas on 02.03.17.
 */
@RestController
@RequestMapping("/customer/{cid}/partnerMapping")
public class PartnerMappingController extends ApiController {
	@RequestMapping(method = RequestMethod.GET, value = "/{pid}")
	public ResponseEntity<PartnerMapping> getPartnerMapping(@PathVariable Long cid, @PathVariable Long pid) throws ResourceException {
		PartnerMapping partnerMapping = partnerMappingService.findPartnerMappingById(pid);
		if (partnerMapping == null || !cid.equals(partnerMapping.getCustomer().getId())) {
			throw new ResourceNotFoundException();
		}
		return new ResponseEntity<>(partnerMapping, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "")
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

	@RequestMapping(method = RequestMethod.PUT, value = "/{pid}")
	public ResponseEntity<PartnerMapping> updatePartnerMapping(@RequestBody PartnerMapping postPartnerMapping,
	                                                           @PathVariable Long cid, @PathVariable Long pid) throws ResourceException {
		PartnerMapping partnerMapping = findPartnerMapping(cid, pid);
		partnerMapping.copyData(postPartnerMapping);
		partnerMappingService.save(partnerMapping);

		return new ResponseEntity<>(partnerMapping, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{pid}")
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
}
