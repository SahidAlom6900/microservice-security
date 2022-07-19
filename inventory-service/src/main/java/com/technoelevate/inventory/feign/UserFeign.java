package com.technoelevate.inventory.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.technoelevate.inventory.response.RoleResponse;


@FeignClient(name = "user-service",url = "http://localhost:8399/")
public interface UserFeign {
	
	@GetMapping(path = "api/v1/authentication/{username}/{password}")
	public ResponseEntity<RoleResponse> getUserName(@PathVariable(name = "username") String username);


}
