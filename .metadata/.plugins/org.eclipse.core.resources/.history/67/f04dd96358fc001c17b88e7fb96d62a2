package com.technoelevate.order_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technoelevate.order_service.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	public Optional<List<Order>> findByOrderId(Long orderId);

}
