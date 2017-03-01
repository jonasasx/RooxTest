package com.jonasasx.roox.test;

import com.jonasasx.roox.test.entities.Customer;
import com.jonasasx.roox.test.entities.PartnerMapping;
import com.jonasasx.roox.test.services.CustomerService;
import com.jonasasx.roox.test.services.PartnerMappingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JPATests {
	@Autowired
	private CustomerService customerService;

	@Autowired
	private PartnerMappingService partnerMappingService;

	@Test
	public void crudDbCustomer() {
		Customer customer = createCustomer();

		customerService.save(customer);

		Long id = customer.getId();
		Assert.assertNotNull(id);

		Customer loadedCustomer = customerService.findById(id);
		Assert.assertEquals(customer, loadedCustomer);

		loadedCustomer.setBalance(new BigDecimal(50));
		customerService.save(loadedCustomer);

		loadedCustomer = customerService.findById(id);
		Assert.assertEquals(loadedCustomer.getBalance().doubleValue(), new BigDecimal(50).doubleValue(), 0.01);

		customerService.delete(loadedCustomer);

		loadedCustomer = customerService.findById(id);
		Assert.assertNull(loadedCustomer);
	}

	@Test
	public void crudDbPartnerMapping() {
		Customer customer = createCustomer();
		customer = customerService.save(customer);

		PartnerMapping partnerMapping = new PartnerMapping();
		partnerMapping.setFio(customer.getFio());
		partnerMapping.setAccountId("111");
		partnerMapping.setPartnerId("000");
		partnerMapping.setAvatarUrl("https://example.com/uploads/image.png");
		customer.addPartnerMapping(partnerMapping);

		partnerMapping = new PartnerMapping();
		partnerMapping.setFio(customer.getFio());
		partnerMapping.setAccountId("111");
		partnerMapping.setPartnerId("YYY");
		partnerMapping.setAvatarUrl("https://example.com/uploads/image2.png");
		customer.addPartnerMapping(partnerMapping);
		customer = customerService.save(customer);

		Customer loadedCustomer = customerService.findById(customer.getId());

		Assert.assertEquals(loadedCustomer.getPartnerMappings().size(), 2);

		Long id = loadedCustomer.getPartnerMappings().iterator().next().getId();
		PartnerMapping loadedPartnerMapping = partnerMappingService.findPartnerMappingById(id);
		Assert.assertNotNull(loadedPartnerMapping);

		customer.removePartnerMapping(customer.getPartnerMappings().iterator().next());
		customer = customerService.save(customer);

		loadedPartnerMapping = partnerMappingService.findPartnerMappingById(id);
		Assert.assertNull(loadedPartnerMapping);

		customerService.delete(loadedCustomer);
	}

	private Customer createCustomer() {
		Customer customer = new Customer();
		customer.setFio("Paul");
		customer.setLogin("paul");
		customer.setPassword("paulpaul");
		customer.setBalance(new BigDecimal(100));
		return customer;
	}

}
