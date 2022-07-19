package com.technoelevate.inventory.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderResponse implements Serializable {

	private boolean error;
	
	private String message;
	
	private Object data;
	
}