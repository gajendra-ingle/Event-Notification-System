package com.sprih.eventnotificationsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventRequest {
	@NotBlank(message = "Event type is required")
	private String eventType;

	@NotNull(message = "Payload is required")
	private Object payload;

	@NotBlank(message = "Callback URL is required")
	private String callbackUrl;
}
