package com.sprih.eventnotificationsystem.processor;

import com.sprih.eventnotificationsystem.model.Event;

public interface EventProcessor {
	
    void process(Event event);
}

