package com.technoelevate.order_service.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.technoelevate.order_service.dto.OrderDto;
import com.technoelevate.order_service.entity.Order;
import com.technoelevate.order_service.feign.ProductOrderFeign;
import com.technoelevate.order_service.feign.UserOrderFeign;
import com.technoelevate.order_service.repository.OrderRepository;
import com.technoelevate.order_service.response.dto.LoggedInUser;
import com.technoelevate.order_service.response.dto.Product;
import com.technoelevate.order_service.response.dtoresponse.ProductResponse;
import com.technoelevate.order_service.response.dtoresponse.UserResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;

	private ProductOrderFeign productOrderFeign;

	private UserOrderFeign userFeign;

	private Order createOrder(OrderDto roderDto) {
		Order order = Order.builder().build();
		BeanUtils.copyProperties(roderDto == null ? OrderDto.builder().build() : roderDto, order);
		ResponseEntity<ProductResponse> orderProduct = productOrderFeign.orderProduct(order.getProductId(),
				order.getQuentity());
		Product product = Optional
				.ofNullable(Optional.ofNullable(orderProduct).orElseThrow(IllegalStateException::new).getBody())
				.orElseThrow(IllegalStateException::new).getData();
		BeanUtils.copyProperties(product, order);
		order.setTotalPrice(product.getUnitPrice() * order.getQuentity() * (100 - product.getDiscount()) / 100);

		ResponseEntity<UserResponse> userResponse = userFeign.getLoggedInUser();

		LoggedInUser loggedInUser = Optional
				.ofNullable(Optional.ofNullable(userResponse).orElseThrow(IllegalStateException::new).getBody())
				.orElseThrow(IllegalStateException::new).getData();

		order.setUserId(loggedInUser.getUserId());

		return order;
	}

	@Override
	public List<Order> genarateOrder(List<OrderDto> orderDtos) {
		log.info("===Inside Order Servive===");
		long orderId = setOrderId();
		List<Order> orders = orderDtos.isEmpty() ? Collections.emptyList() : orderDtos.stream().map(orderDto -> {
			orderDto.setOrderId(orderId);
			return createOrder(orderDto);
		}).collect(Collectors.toList());
		log.info("===Save Order===");
		return orderRepository.saveAll(orders);
	}

	@Override
	public Order getOrder(Long id) {
		log.info("===Fatch Order With Id :" + id);
		return orderRepository.findById(id).orElseThrow(IllegalStateException::new);
	}

	@Override
	public List<Order> getAllOrder(Long orderId) {
		log.info("===Fatch All Order With Order Id :" + orderId);
		return orderRepository.findByOrderId(orderId).orElseThrow(IllegalStateException::new);
	}

	private Long setOrderId() {
		List<Long> orders = orderRepository.findAll().stream().map(Order::getOrderId).collect(Collectors.toList());
		long orderId = ThreadLocalRandom.current().nextLong(1000000, 1000000000);
		while (orders.contains(orderId)) {
			orderId = ThreadLocalRandom.current().nextLong(1000000, 1000000000);
		}
		return orderId;
	}

}