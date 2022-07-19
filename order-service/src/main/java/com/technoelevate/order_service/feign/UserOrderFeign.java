package com.technoelevate.order_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.technoelevate.order_service.response.dtoresponse.UserResponse;


@FeignClient(name = "USER-SERVICE")
public interface UserOrderFeign {
	
	@GetMapping(path = "api/v1/user")
	public ResponseEntity<UserResponse> getLoggedInUser();


}
