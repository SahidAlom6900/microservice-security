package com.technoelevate.auth.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.technoelevate.auth.entity.Permission;
import com.technoelevate.auth.exception.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserdetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("===Inside Load User By Usename===");
		com.technoelevate.auth.entity.User user = userRepository.findByUsername(username)
				.orElseThrow(IllegalStateException::new);
		Set<String> collect = user.getRoles().stream()
				.flatMap(per -> per.getPermissions().stream().map(Permission::getPermissionName))
				.collect(Collectors.toSet());
		List<SimpleGrantedAuthority> authorities = collect.stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		return new User(username, user.getPassword(), authorities);
	}

}
