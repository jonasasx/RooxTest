package com.jonasasx.roox.test.security;

import com.jonasasx.roox.test.entities.Customer;
import com.jonasasx.roox.test.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Created by ionas on 02.03.17.
 */
@Component
public class CustomerIdAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomerService customerService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Object principal = authentication.getPrincipal();
		Long id = null;
		try {
			if (principal == null || !(principal instanceof String) || (id = Long.valueOf((String) principal)) == null) {
				throw new OAuth2Exception("Principal has illegal format");
			}
		} catch (NumberFormatException e) {
			throw new OAuth2Exception("Customer id must be integer");
		}
		Customer customer = customerService.findById(id);
		if (customer == null) {
			throw new OAuth2Exception("Customer not found");
		}
		return new PreAuthenticatedAuthenticationToken(customer, null, Collections.EMPTY_LIST);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
