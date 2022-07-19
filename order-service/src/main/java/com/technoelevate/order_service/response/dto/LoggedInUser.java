package com.technoelevate.order_service.response.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(value = Include.NON_DEFAULT)
public class LoggedInUser implements Serializable{
	
	private Long userId;

	private String username;

	private String firstName;

	private String lastName;

	private String gender;
}
