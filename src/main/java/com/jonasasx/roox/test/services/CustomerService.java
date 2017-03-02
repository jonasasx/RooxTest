package com.jonasasx.roox.test.services;

import com.jonasasx.roox.test.entities.Customer;

/**
 * Customer Service
 * <p>
 * Created by ionas on 01.03.17.
 */
public interface CustomerService {

	/**
	 * Save Customer
	 *
	 * @param customer Customer
	 * @return Customer
	 */
	Customer save(Customer customer);

	/**
	 * Find Customer by primary key
	 *
	 * @param id primary key
	 * @return Customer
	 */
	Customer findById(Long id);

	/**
	 * Delete Customer
	 *
	 * @param customer Customer to be deleted
	 */
	void delete(Customer customer);

	/**
	 * Find Customer by username
	 *
	 * @param username Username
	 * @return Customer
	 */
	Customer findByUsername(String username);
}
