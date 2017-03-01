package com.jonasasx.roox.test;

import com.jonasasx.roox.test.entities.Customer;
import com.jonasasx.roox.test.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ionas on 01.03.17.
 */

@RestController
public class ApiController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(method = RequestMethod.GET, value = "/user/{cid}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Long cid) {
		return new ResponseEntity<Customer>(customerService.findById(cid), HttpStatus.OK);
	}

}
