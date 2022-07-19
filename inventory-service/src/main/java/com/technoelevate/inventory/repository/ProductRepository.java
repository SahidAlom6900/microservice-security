package com.technoelevate.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technoelevate.inventory.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
Optional<Product>	findByProductIdAndProductCountGreaterThanEqual(Long productId,Long productCount);

}
