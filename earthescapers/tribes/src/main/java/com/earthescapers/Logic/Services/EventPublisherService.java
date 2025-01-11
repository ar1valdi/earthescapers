package com.earthescapers.Logic.Services;
import com.earthescapers.Models.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EventPublisherService {
    private String eventListenerUrl;

    private final RestTemplate restTemplate;

    public EventPublisherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        eventListenerUrl = "http://localhost:8081/event";
    }

    public void notifyCategoryAdded(String categoryId) {
        Event event = new Event("TRIBE_ADDED", categoryId);
        sendEvent(event);
    }

    public void notifyCategoryRemoved(String categoryId) {
        Event event = new Event("TRIBE_REMOVED", categoryId);
        sendEvent(event);
    }

    private void sendEvent(Event event) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(eventListenerUrl, event, String.class);
            System.out.println("Event sent successfully: " + response.getStatusCode());
        } catch (Exception e) {
            System.err.println("Failed to send event: " + e.getMessage());
        }
    }
}
