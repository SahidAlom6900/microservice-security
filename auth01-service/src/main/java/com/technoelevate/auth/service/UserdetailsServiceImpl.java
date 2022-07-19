package com.technoelevate.auth.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserdetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("===Inside Load User By Usename===");
		List<String> roles = List.of("USER", "ADMIN","SUPER ADMIN");
		List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		return new User(username, "$2a$10$6TjONoJDxKXGY1uY1L6KdeYmEc.fyj5UBPKMV02fXsJvQcl5S88HO", authorities);
	}

}
