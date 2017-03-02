package com.jonasasx.roox.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonasasx.roox.test.entities.Customer;
import com.jonasasx.roox.test.entities.PartnerMapping;
import com.jonasasx.roox.test.services.CustomerService;
import com.jonasasx.roox.test.services.PartnerMappingService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ionas on 01.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTests {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext context;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PartnerMappingService partnerMappingService;

	private MockMvc mockMvc;

	private Customer me;
	private Customer he;


	@Before
	public void setup() throws Exception {

		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();

		me = new Customer("Ionas", "ionas", "password", new BigDecimal(100));

		PartnerMapping pm = new PartnerMapping("vkapp001", "2207", "https://example.com/uploads/ava.jpg");
		pm.setCustomer(me);

		pm = new PartnerMapping("fb001", "ae23407", "https://example.com/uploads/ava.jpg");
		pm.setCustomer(me);

		customerService.save(me);

		he = new Customer("Paul", "paul", "foobar", new BigDecimal(200));
		pm = new PartnerMapping("vkapp001", "2208", "https://example.com/uploads/ava2.jpg");
		pm.setCustomer(he);

		pm = new PartnerMapping("fb001", "ae23408", "https://example.com/uploads/ava2.jpg");
		pm.setCustomer(he);

		customerService.save(he);
	}

	@After
	public void after() throws Exception {
		customerService.delete(customerService.findById(me.getId()));
		customerService.delete(customerService.findById(he.getId()));
	}

	@Test
	public void authorization() throws Exception {
		mockMvc.perform(
				get("/customer/" + me.getId()))
				.andExpect(status().isUnauthorized());

		mockMvc.perform(
				get("/customer/" + me.getId()).header("Authorization", "Bearer 0"))
				.andExpect(status().isUnauthorized());

		mockMvc.perform(
				get("/customer/" + me.getId()).header("Authorization", "Bearer " + me.getId()))
				.andExpect(status().isOk());
	}

	@Test
	public void readSingleCustomer() throws Exception {
		mockMvc.perform(
				get("/customer/" + me.getId()).header("Authorization", "Bearer " + me.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(me.getId().intValue())))
				.andExpect(jsonPath("$.login", is(me.getLogin())))
				.andExpect(jsonPath("$.balance", is(me.getBalance().doubleValue())));

		mockMvc.perform(
				get("/customer/" + (me.getId() - 1)).header("Authorization", "Bearer " + me.getId()))
				.andExpect(status().isForbidden());
	}

	@Test
	public void createPartnerMapping() throws Exception {
		PartnerMapping pm = new PartnerMapping("fb001", "ae2340x" + System.currentTimeMillis(), "https://example.com/uploads/ava0.jpg");
		String json = objectMapper.writeValueAsString(pm);
		ResultActions request = mockMvc.perform(
				post("/customer/" + me.getId() + "/partnerMapping")
						.header("Authorization", "Bearer " + me.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isCreated())
				.andExpect(header().string(LOCATION, containsString("/customer/" + me.getId() + "/partnerMapping/")));

		String location = request.andReturn().getResponse().getHeader(LOCATION);

		mockMvc.perform(
				post("/customer/" + me.getId() + "/partnerMapping")
						.header("Authorization", "Bearer " + me.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isConflict());

		mockMvc.perform(
				get(location).header("Authorization", "Bearer " + me.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.accountId", is(pm.getAccountId())));
	}

	@Test
	public void updatePartnerMapping() throws Exception {
		PartnerMapping pm = new PartnerMapping("fb001", "ae2340x" + System.currentTimeMillis(), "https://example.com/uploads/ava0.jpg");
		String json = objectMapper.writeValueAsString(pm);
		ResultActions request = mockMvc.perform(
				post("/customer/" + me.getId() + "/partnerMapping")
						.header("Authorization", "Bearer " + me.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isCreated())
				.andExpect(header().string(LOCATION, containsString("/customer/" + me.getId() + "/partnerMapping/")));

		String location = request.andReturn().getResponse().getHeader(LOCATION);

		mockMvc.perform(
				get(location).header("Authorization", "Bearer " + me.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.accountId", is(pm.getAccountId())));

		pm.setAccountId("ae2340x" + System.currentTimeMillis() + "2");
		json = objectMapper.writeValueAsString(pm);
		mockMvc.perform(
				put(location)
						.header("Authorization", "Bearer " + me.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.accountId", is(pm.getAccountId())));
	}

	@Test
	public void deletePartnerMapping() throws Exception {
		PartnerMapping pm = new PartnerMapping("fb001", "ae2340x" + System.currentTimeMillis(), "https://example.com/uploads/ava0.jpg");
		String json = objectMapper.writeValueAsString(pm);
		ResultActions request = mockMvc.perform(
				post("/customer/" + me.getId() + "/partnerMapping")
						.header("Authorization", "Bearer " + me.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isCreated())
				.andExpect(header().string(LOCATION, containsString("/customer/" + me.getId() + "/partnerMapping/")));

		String location = request.andReturn().getResponse().getHeader(LOCATION);

		mockMvc.perform(
				get(location).header("Authorization", "Bearer " + me.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.accountId", is(pm.getAccountId())));

		mockMvc.perform(
				delete(location).header("Authorization", "Bearer " + me.getId()))
				.andExpect(status().isNoContent());

		mockMvc.perform(
				get(location).header("Authorization", "Bearer " + me.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testMe() throws Exception {
		mockMvc.perform(
				get("/customer/@me").header("Authorization", "Bearer " + me.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(me.getId().intValue())));

		mockMvc.perform(
				get("/customer/@me/partnerMapping/" + me.getPartnerMappings().iterator().next().getId())
						.header("Authorization", "Bearer " + me.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType));
	}

}
