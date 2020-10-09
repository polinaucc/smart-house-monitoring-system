package ua.polina.smart_house_monitoring_system.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.polina.smart_house_monitoring_system.api.EmergencyData;
import ua.polina.smart_house_monitoring_system.api.MessageList;

/**
 * The Scheduler.
 */
@Component
public class Scheduler {
    /**
     * Checks the house for emergencies each 10 sec.
     */
    @Scheduled(cron = "10 * * * * * ")
    public void check() {
        final String uri = "http://localhost:8081/sensor/check";
        RestTemplate restTemplate = new RestTemplate();
        MessageList response = restTemplate.getForObject(
                uri, MessageList.class);
        EmergencyData.getInstance(response);
    }
}
