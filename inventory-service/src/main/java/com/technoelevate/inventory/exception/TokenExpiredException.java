package com.technoelevate.inventory.exception;

@SuppressWarnings("serial")
public class TokenExpiredException  extends RuntimeException {
	
	public TokenExpiredException(String message) {
		super(message);
	}

}