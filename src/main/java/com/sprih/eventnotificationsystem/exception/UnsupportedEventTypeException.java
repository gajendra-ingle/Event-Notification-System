package com.sprih.eventnotificationsystem.exception;

public class UnsupportedEventTypeException extends RuntimeException {

	static final long serialVersionUID = 1L;

	public UnsupportedEventTypeException(String type) {
		super("Unsupported event type: " + type);
	}
}