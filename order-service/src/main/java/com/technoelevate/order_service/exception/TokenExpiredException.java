package com.technoelevate.order_service.exception;

@SuppressWarnings("serial")
public class TokenExpiredException  extends RuntimeException {
	
	public TokenExpiredException(String message) {
		super(message);
	}

}