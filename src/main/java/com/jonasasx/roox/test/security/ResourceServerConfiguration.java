package com.jonasasx.roox.test.security;

import com.jonasasx.roox.test.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Resource Server Configuration
 * <p>
 * Created by ionas on 02.03.17.
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	private static final String RESOURCE_ID = "rest_api";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomerService customerService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false).authenticationManager(authenticationManager).accessDeniedHandler(new AccessDeniedHandler() {
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
			}
		}).authenticationEntryPoint(new AuthenticationEntryPoint() {
			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.
				anonymous().disable()
				.requestMatchers().antMatchers("/customer/**")
				.and().authorizeRequests()
				.antMatchers("/customer/**").access("isAuthenticated()");
	}

	/**
	 * Authentication Manager
	 *
	 * @param customerIdAuthenticationProvider Customer's Authentication Provider
	 * @return Authentication Manager
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManager(CustomerIdAuthenticationProvider customerIdAuthenticationProvider) throws Exception {
		List<AuthenticationProvider> providers = new ArrayList<>();
		providers.add(customerIdAuthenticationProvider);
		return new ProviderManager(providers);
	}


}