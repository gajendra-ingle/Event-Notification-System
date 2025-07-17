package com.sprih.eventnotificationsystem.service;

import com.sprih.eventnotificationsystem.dto.EventRequest;

public interface EventService {
    void handleEvent(String eventId, EventRequest request);
}
