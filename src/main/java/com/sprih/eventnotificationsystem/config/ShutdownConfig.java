package com.sprih.eventnotificationsystem.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.sprih.eventnotificationsystem.service.Impl.EventServiceImpl;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShutdownConfig {

    private final EventServiceImpl eventService;

    @PreDestroy
    public void onShutdown() {
        log.info("Initiating graceful shutdown...");
        for (ExecutorService executor : eventService.getExecutors().values()) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    log.warn("Executor did not terminate in time, forcing shutdown.");
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                log.error("Shutdown interrupted: {}", e.getMessage());
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        log.info("All executors shut down cleanly.");
    }
}
