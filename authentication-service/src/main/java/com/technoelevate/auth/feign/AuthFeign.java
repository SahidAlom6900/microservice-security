package com.technoelevate.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.technoelevate.auth.response.RoleResponse;


@FeignClient(name = "auth-service",url = "http://localhost:9090/")
public interface AuthFeign {
	
	@GetMapping(path = "api/v1/user-name/{username}")
	public ResponseEntity<RoleResponse> getUserName(@PathVariable(name = "username") String username);


}
