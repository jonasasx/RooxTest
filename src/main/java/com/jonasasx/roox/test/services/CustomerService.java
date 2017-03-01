package com.jonasasx.roox.test.services;

import com.jonasasx.roox.test.entities.Customer;

/**
 * Created by ionas on 01.03.17.
 */

public interface CustomerService {
	Customer save(Customer customer);

	Customer findById(Long id);

	void delete(Customer customer);
}
