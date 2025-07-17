package com.sprih.eventnotificationsystem.processor.Impl;

import org.springframework.stereotype.Component;

import com.sprih.eventnotificationsystem.model.Event;
import com.sprih.eventnotificationsystem.processor.EventProcessor;
import com.sprih.eventnotificationsystem.util.CallbackNotifier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SmsProcessor implements EventProcessor {

	@Override
	public void process(Event event) {
		log.info("Processing SMS event: {}", event.getEventId());
		try {
			// SMS processing delay of 3 seconds
			Thread.sleep(3000);

			// Random failure in 10% of the cases
			if (Math.random() < 0.1) {
				throw new RuntimeException("Simulated SMS failure");
			}
			log.info("SMS sent successfully for eventId: {}", event.getEventId());
			CallbackNotifier.sendSuccess(event);
		} catch (Exception e) {
			log.error("SMS event failed: {}", event.getEventId(), e);
			CallbackNotifier.sendFailure(event, e.getMessage());
		}
	}
}
