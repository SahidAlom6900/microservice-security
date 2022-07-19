package com.technoelevate.order_service.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.technoelevate.order_service.response.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(value = Include.NON_DEFAULT)
public class RoleResponse implements Serializable {

	private boolean error;
	
	private String message;
	
	private Product data;
	
}
