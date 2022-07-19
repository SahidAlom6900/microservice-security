package com.technoelevate.order_service.response.dtoresponse;

import java.io.Serializable;

import com.technoelevate.order_service.response.dto.LoggedInUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse implements Serializable {

	private boolean error;
	
	private String message;
	
	private LoggedInUser data;
	
}