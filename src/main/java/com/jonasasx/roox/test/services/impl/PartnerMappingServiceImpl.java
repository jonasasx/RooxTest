package com.jonasasx.roox.test.services.impl;

import com.jonasasx.roox.test.entities.PartnerMapping;
import com.jonasasx.roox.test.services.PartnerMappingService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Partner Mapping Service Implementation
 * <p>
 * Created by ionas on 01.03.17.
 */
@Service
@Transactional
public class PartnerMappingServiceImpl implements PartnerMappingService {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PartnerMapping findPartnerMappingById(Long id) {
		return entityManager.find(PartnerMapping.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(PartnerMapping partnerMapping) {
		return entityManager.createQuery("FROM PartnerMapping WHERE partnerId=:partnerId AND accountId=:accountId")
				.setParameter("partnerId", partnerMapping.getPartnerId())
				.setParameter("accountId", partnerMapping.getAccountId()).getResultList().iterator().hasNext();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PartnerMapping save(PartnerMapping partnerMapping) {
		if (partnerMapping.getId() == null) {
			entityManager.persist(partnerMapping);
			return partnerMapping;
		}
		return entityManager.merge(partnerMapping);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(PartnerMapping partnerMapping) {
		partnerMapping = entityManager.contains(partnerMapping) ? partnerMapping : entityManager.merge(partnerMapping);
		partnerMapping.setCustomer(null);
		entityManager.remove(partnerMapping);
	}
}
