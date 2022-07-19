package com.technoelevate.order_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.technoelevate.order_service.dto.OrderDto;
import com.technoelevate.order_service.response.OrderResponse;
import com.technoelevate.order_service.service.OrderService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1")
@AllArgsConstructor
public class OrderController {

	private final OrderService userService;

	@PreAuthorize("hasAnyAuthority('create','update')")
	@PostMapping(path = "order")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<OrderResponse> generateOrder(@RequestBody List<OrderDto> orderDtos) {
		return ResponseEntity.ok(
				OrderResponse.builder().error(false).message("").data(userService.genarateOrder(orderDtos)).build());
	}

	@PreAuthorize("hasAuthority('read')")
	@GetMapping(path = "order/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<OrderResponse> getOrder(@PathVariable(name = "id") Long id) {
		return ResponseEntity
				.ok(OrderResponse.builder().error(false).message("").data(userService.getOrder(id)).build());
	}

	@PreAuthorize("hasAuthority('read')")
	@GetMapping(path = "orders/{orderId}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<OrderResponse> getAllDepartment(@PathVariable(name = "orderId") Long orderId) {
		return ResponseEntity
				.ok(OrderResponse.builder().error(false).message("").data(userService.getAllOrder(orderId)).build());
	}

}
