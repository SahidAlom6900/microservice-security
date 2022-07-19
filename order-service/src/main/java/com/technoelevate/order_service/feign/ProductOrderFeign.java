package com.technoelevate.order_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.technoelevate.order_service.response.dtoresponse.ProductResponse;

@FeignClient(name = "INVENTORY-SERVICE")
public interface ProductOrderFeign {
	
	@GetMapping(path = "api/v1/product/{productId}/{quentity}")
	public ResponseEntity<ProductResponse> orderProduct(@PathVariable(name = "productId") Long productId,
			@PathVariable(name = "quentity") Long quentity);

}
