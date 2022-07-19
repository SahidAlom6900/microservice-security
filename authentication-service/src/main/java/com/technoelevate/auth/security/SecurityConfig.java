package com.technoelevate.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.technoelevate.auth.exception.CustomAuthenticationEntryPoint;
import com.technoelevate.auth.filter.CustomAuthenticattionFilter;
import com.technoelevate.auth.filter.CustomAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticattionFilter authenticattionFilter,
			CustomAuthorizationFilter authorizationFilter, BCryptPasswordEncoder passwordEncoder,
			UserDetailsService userDetailsService) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		authenticattionFilter.setFilterProcessesUrl("/api/v1/auth");
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/api/v1/auth").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
		http.addFilter(authenticattionFilter);
		http.authenticationManager(authenticationManager);
		http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
