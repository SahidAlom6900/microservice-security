package com.technoelevate.order_service.service;

import java.util.List;

import com.technoelevate.order_service.dto.OrderDto;
import com.technoelevate.order_service.entity.Order;

public interface OrderService {

	public List<Order> genarateOrder(List<OrderDto> orderDtos);
	
	public Order getOrder(Long deptId);
	
	public List<Order> getAllOrder(Long orderId);
	
}
