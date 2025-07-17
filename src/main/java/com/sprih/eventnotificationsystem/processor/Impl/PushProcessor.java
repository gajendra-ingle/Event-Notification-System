package com.sprih.eventnotificationsystem.processor.Impl;

import org.springframework.stereotype.Component;

import com.sprih.eventnotificationsystem.model.Event;
import com.sprih.eventnotificationsystem.processor.EventProcessor;
import com.sprih.eventnotificationsystem.util.CallbackNotifier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PushProcessor implements EventProcessor {

	@Override
	public void process(Event event) {
		log.info("Processing PUSH event: {}", event.getEventId());
		try {
			// Push notification delay of 2 seconds
			Thread.sleep(2000);

			// Random failure in 10% of the cases
			if (Math.random() < 0.1) {
				throw new RuntimeException("Simulated PUSH failure");
			}
			log.info("Push notification sent for eventId: {}", event.getEventId());
			CallbackNotifier.sendSuccess(event);
		} catch (Exception e) {
			log.error("PUSH event failed: {}", event.getEventId(), e);
			CallbackNotifier.sendFailure(event, e.getMessage());
		}
	}
}
