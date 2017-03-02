package com.jonasasx.roox.test.services;

import com.jonasasx.roox.test.entities.PartnerMapping;

/**
 * Created by ionas on 01.03.17.
 */
public interface PartnerMappingService {

	/**
	 * Find Partner's mapping by id
	 *
	 * @param id Partner's mapping id
	 * @return Partner's mapping
	 */
	PartnerMapping findPartnerMappingById(Long id);

	/**
	 * Does Partner's mapping exist
	 *
	 * @param partnerMapping Partner's mapping
	 * @return Does Partner's mapping exist
	 */
	boolean exists(PartnerMapping partnerMapping);

	/**
	 * Save Partner's mapping (insert or update)
	 *
	 * @param partnerMapping Partner's mapping
	 * @return Partner's mapping
	 */
	PartnerMapping save(PartnerMapping partnerMapping);

	/**
	 * Delete Partner's mapping
	 *
	 * @param partnerMapping Partner's mapping
	 */
	void delete(PartnerMapping partnerMapping);
}
