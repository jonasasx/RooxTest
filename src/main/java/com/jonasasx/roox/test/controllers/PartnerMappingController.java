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

import javax.validation.Valid;

/**
 * Partner mapping controller
 * <p>
 * Created by ionas on 02.03.17.
 */
@RestController
@RequestMapping("/customer/{cid}/partnerMapping")
public class PartnerMappingController extends ApiController {

	/**
	 * Get Partner's mapping by customer's id and partner's mapping id
	 *
	 * @param cid customer's id
	 * @param pid partner's mapping id
	 * @return Partner's mapping
	 * @throws ResourceException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{pid}")
	public ResponseEntity<PartnerMapping> getPartnerMapping(@PathVariable Long cid, @PathVariable Long pid) throws ResourceException {
		PartnerMapping partnerMapping = partnerMappingService.findPartnerMappingById(pid);
		if (partnerMapping == null || !cid.equals(partnerMapping.getCustomer().getId())) {
			throw new ResourceNotFoundException();
		}
		return new ResponseEntity<>(partnerMapping, HttpStatus.OK);
	}

	/**
	 * Create Partner's mapping to customer
	 *
	 * @param partnerMapping Partner's mapping to be created
	 * @param cid            customer's id
	 * @param ucBuilder      Uri Components Builder
	 * @return response
	 * @throws ResourceException
	 */
	@RequestMapping(method = RequestMethod.POST, value = "")
	public ResponseEntity<?> createPartnerMapping(@Valid @RequestBody PartnerMapping partnerMapping,
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

	/**
	 * Update Partner's mapping by customer's id and partner's mapping id
	 *
	 * @param postPartnerMapping Partner's mapping to be updated
	 * @param cid                customer's id
	 * @param pid                partner's mapping id
	 * @return Partner's mapping
	 * @throws ResourceException
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{pid}")
	public ResponseEntity<PartnerMapping> updatePartnerMapping(@Valid @RequestBody PartnerMapping postPartnerMapping,
	                                                           @PathVariable Long cid, @PathVariable Long pid) throws ResourceException {
		PartnerMapping partnerMapping = findPartnerMapping(cid, pid);
		partnerMapping.copyData(postPartnerMapping);
		partnerMappingService.save(partnerMapping);

		return new ResponseEntity<>(partnerMapping, HttpStatus.OK);
	}

	/**
	 * Delete Partner's mapping by customer's id and partner's mapping id
	 *
	 * @param cid customer's id
	 * @param pid partner's mapping id
	 * @return response
	 * @throws ResourceException
	 */
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
