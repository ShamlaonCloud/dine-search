package com.anz.dinesearch.exception;

public class RestaurantNotFoundException extends RuntimeException {
	public RestaurantNotFoundException(String message) {
		super(message);
	}
}
