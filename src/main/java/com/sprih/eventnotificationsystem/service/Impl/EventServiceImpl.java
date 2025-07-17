package com.sprih.eventnotificationsystem.service.Impl;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprih.eventnotificationsystem.dto.EventRequest;
import com.sprih.eventnotificationsystem.enums.EventType;
import com.sprih.eventnotificationsystem.exception.UnsupportedEventTypeException;
import com.sprih.eventnotificationsystem.model.Event;
import com.sprih.eventnotificationsystem.processor.EventProcessor;
import com.sprih.eventnotificationsystem.processor.Impl.EmailProcessor;
import com.sprih.eventnotificationsystem.processor.Impl.PushProcessor;
import com.sprih.eventnotificationsystem.processor.Impl.SmsProcessor;
import com.sprih.eventnotificationsystem.service.EventService;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Getter
public class EventServiceImpl implements EventService {

	private final Map<EventType, BlockingQueue<Event>> eventQueues = new EnumMap<>(EventType.class);
	private final Map<EventType, ExecutorService> executors = new EnumMap<>(EventType.class);

	@Autowired
	private EmailProcessor emailProcessor;

	@Autowired
	private SmsProcessor smsProcessor;

	@Autowired
	private PushProcessor pushProcessor;

	@PostConstruct
	public void init() {
		for (EventType type : EventType.values()) {
			eventQueues.put(type, new LinkedBlockingQueue<>());
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executors.put(type, executor);
			EventProcessor processor = getProcessor(type);

			executor.submit(() -> {
				log.info("Started processor thread for {}", type);
				try {
					while (true) {
						Event event = eventQueues.get(type).take();
						processor.process(event);
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					log.warn("Processor thread interrupted for {}", type);
				}
			});
		}
	}

	@Override
	public void handleEvent(String eventId, EventRequest request) {
		try {
			EventType type = EventType.valueOf(request.getEventType().toUpperCase());
			Event event = new Event(eventId, type, request.getPayload(), request.getCallbackUrl());
			eventQueues.get(type).offer(event);
			log.info("Event queued: {} for type {}", eventId, type);
		} catch (IllegalArgumentException e) {
			log.error("Invalid event type received: {}", request.getEventType());
			throw new UnsupportedEventTypeException(request.getEventType());
		}
	}

	private EventProcessor getProcessor(EventType type) {
		return switch (type) {
			case EMAIL -> emailProcessor;
			case SMS -> smsProcessor;
			case PUSH -> pushProcessor;
		};
	}
}