package com.jonasasx.roox.test.services.impl;

import com.jonasasx.roox.test.entities.PartnerMapping;
import com.jonasasx.roox.test.services.PartnerMappingService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by ionas on 01.03.17.
 */
@Service
@Transactional
public class PartnerMappingServiceImpl implements PartnerMappingService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PartnerMapping findPartnerMappingById(Long id) {
		return entityManager.find(PartnerMapping.class, id);
	}
}
