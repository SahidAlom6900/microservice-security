package com.technoelevate.inventory.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class UserRequestIntercentor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String token = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername()
				: principal.toString();
		template.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
	}
}
