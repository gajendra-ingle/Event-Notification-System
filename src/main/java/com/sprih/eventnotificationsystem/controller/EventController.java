package com.sprih.eventnotificationsystem.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprih.eventnotificationsystem.dto.EventRequest;
import com.sprih.eventnotificationsystem.dto.EventResponse;
import com.sprih.eventnotificationsystem.service.EventService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/events")
public class EventController {

	@Autowired
	private EventService eventService;

	@PostMapping
	public ResponseEntity<?> createEvent(@Valid @RequestBody EventRequest request) {
		String eventId = UUID.randomUUID().toString();
		log.info("Received new event: {}", eventId);
		eventService.handleEvent(eventId, request);
		return ResponseEntity.ok(new EventResponse(eventId, "Event accepted for processing"));

	}
}
