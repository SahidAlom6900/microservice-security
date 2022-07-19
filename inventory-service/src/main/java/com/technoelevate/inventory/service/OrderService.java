package com.technoelevate.inventory.service;

import java.util.List;

import com.technoelevate.inventory.dto.ProductDto;
import com.technoelevate.inventory.entity.Product;

public interface OrderService {

	public List<Product> addOrUpdateProduct(List<ProductDto> orderDtos);
	
	public Product getProduct(Long productId);
	
	public Product orderProduct(Long productId,Long quantity);
	
	public List<Product> getAllProduct();
	
	public Product deleteProduct(Long deptId);
	
}
