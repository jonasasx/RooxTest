package com.jonasasx.roox.test.controllers;

import com.jonasasx.roox.test.entities.Customer;
import com.jonasasx.roox.test.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Customer controller
 * <p>
 * Created by ionas on 02.03.17.
 */
@RestController
@RequestMapping("/customer")
public class CustomerController extends ApiController {

	/**
	 * Get customer by id
	 *
	 * @param cid customer's id
	 * @return customer
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{cid}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Long cid) throws ResourceNotFoundException {
		Customer customer = customerService.findById(cid);
		if (customer == null) {
			throw new ResourceNotFoundException();
		}
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

}