package com.jonasasx.roox.test.services;

import com.jonasasx.roox.test.entities.PartnerMapping;

/**
 * Created by ionas on 01.03.17.
 */
public interface PartnerMappingService {
	PartnerMapping findPartnerMappingById(Long id);

	boolean exists(PartnerMapping partnerMapping);

	PartnerMapping save(PartnerMapping partnerMapping);

	void delete(PartnerMapping partnerMapping);
}
