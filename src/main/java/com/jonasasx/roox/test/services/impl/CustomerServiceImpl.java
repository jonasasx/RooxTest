package com.jonasasx.roox.test.services.impl;

import com.jonasasx.roox.test.entities.Customer;
import com.jonasasx.roox.test.services.CustomerService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Customer Service Implementation
 * <p>
 * Created by ionas on 01.03.17.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Customer save(Customer customer) {
		if (customer.getId() == null) {
			entityManager.persist(customer);
			return customer;
		}
		return entityManager.merge(customer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Customer findById(Long id) {
		return entityManager.find(Customer.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Customer findByUsername(String username) {
		return entityManager.createQuery("FROM Customer WHERE username = :username", Customer.class).setParameter("username", "username").getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Customer customer) {
		entityManager.remove(entityManager.contains(customer) ? customer : entityManager.merge(customer));
	}


}
