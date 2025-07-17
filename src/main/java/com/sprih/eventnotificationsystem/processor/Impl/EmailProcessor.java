package com.sprih.eventnotificationsystem.processor.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.sprih.eventnotificationsystem.model.Event;
import com.sprih.eventnotificationsystem.processor.EventProcessor;
import com.sprih.eventnotificationsystem.util.CallbackNotifier;

@Slf4j
@Component
public class EmailProcessor implements EventProcessor {

	@Override
	public void process(Event event) {
		log.info("Processing EMAIL event: {}", event.getEventId());
		try {
			// Email processing delay of 5 seconds
			Thread.sleep(5000);

			// Random failure in 10% of the cases
			if (Math.random() < 0.1) {
				throw new RuntimeException("Simulated EMAIL failure");
			}
			log.info("EMAIL sent successfully for eventId: {}", event.getEventId());
			CallbackNotifier.sendSuccess(event);
		} catch (Exception e) {
			log.error("EMAIL event failed: {}", event.getEventId(), e);
			CallbackNotifier.sendFailure(event, e.getMessage());
		}
	}
}