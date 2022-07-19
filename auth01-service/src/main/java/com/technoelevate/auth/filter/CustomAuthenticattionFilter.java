package com.technoelevate.auth.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technoelevate.auth_service.exception.CustomAccessDeniedException;
import com.technoelevate.auth_service.security.JWTConfig;
import com.technoelevate.auth_service.util.JwtUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticattionFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	private CustomAccessDeniedException accessDenied;

	@Autowired
	private JwtUtils jwtUtils;

	public CustomAuthenticattionFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
			CustomAccessDeniedException accessDenied) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.accessDenied = accessDenied;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		log.info("Username is :" + username);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		Authentication authenticate = null;
		try {
			authenticate = authenticationManager.authenticate(authenticationToken);
			log.info("Authentication Successfully!!!");
		} catch (Exception exception) {
			try {
				log.error(exception.getMessage());
				accessDenied.handle(request, response, new AccessDeniedException(exception.getMessage()));
			} catch (Exception exception2) {
				System.out.println(exception2.getMessage());
			}
		}
		return authenticate;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();
		Collection<GrantedAuthority> authorities = user.getAuthorities();
		String[] token = jwtUtils.generateJwtToken(user.getUsername(),user.getAuthorities());
		log.info("Generate access token And refresh token Successfully");
		LinkedHashMap<String, Object> infomation = new LinkedHashMap<>();
		LinkedHashMap<String, Object> tokens = new LinkedHashMap<>();
		infomation.put("error", false);
		infomation.put("message", "Successfully Login " + user.getUsername());
		tokens.put("access_token", token[0]);
		tokens.put("refresh_token", token[1]);
		infomation.put("data", tokens);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), infomation);
	}

}