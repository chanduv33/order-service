package com.storesmanagementsystem.order.exceptions;

@SuppressWarnings("serial")
public class DetailsNotFoundException extends RuntimeException {
	String message;
	public DetailsNotFoundException() {
		this.message = "Details not found exception";
	}
	@Override
	public String getMessage() {
		return this.message;
	}
}
