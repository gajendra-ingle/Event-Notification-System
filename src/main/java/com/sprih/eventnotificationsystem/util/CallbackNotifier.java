package com.sprih.eventnotificationsystem.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprih.eventnotificationsystem.model.Event;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CallbackNotifier {

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void sendSuccess(Event event) {
        send(event.getCallbackUrl(), Map.of(
                "eventId", event.getEventId(),
                "status", "COMPLETED",
                "eventType", event.getEventType().name(),
                "processedAt", Instant.now().toString()
        ));
    }

    public static void sendFailure(Event event, String errorMessage) {
        send(event.getCallbackUrl(), Map.of(
                "eventId", event.getEventId(),
                "status", "FAILED",
                "eventType", event.getEventType().name(),
                "errorMessage", errorMessage,
                "processedAt", Instant.now().toString()
        ));
    }

    private static void send(String urlStr, Map<String, Object> body) {
        try {
            log.info("Sending callback to URL: {}", urlStr);

            String requestBody = objectMapper.writeValueAsString(body);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlStr))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            log.info("Callback response code: {}", response.statusCode());
            log.debug("Callback response body: {}", response.body());

        } catch (Exception e) {
            log.error("Failed to send callback to {}", urlStr, e);
        }
    }
}
