package com.jonasasx.roox.test.services.impl;

import com.jonasasx.roox.test.entities.Customer;
import com.jonasasx.roox.test.services.CustomerService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by ionas on 01.03.17.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Customer save(Customer customer) {
		if (customer.getId() == null) {
			entityManager.persist(customer);
			return customer;
		}
		return entityManager.merge(customer);
	}

	public Customer findById(Long id) {
		return entityManager.find(Customer.class, id);
	}

	@Override
	public void delete(Customer customer) {
		entityManager.remove(entityManager.contains(customer) ? customer : entityManager.merge(customer));
	}
}
