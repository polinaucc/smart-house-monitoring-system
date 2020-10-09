package ua.polina.smart_house_monitoring_system.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.polina.smart_house_monitoring_system.api.EmergencyData;

@Component
public class Scheduler {
    @Scheduled(cron = "10 * * * * * ")
    public void check() {
        final String uri = "http://localhost:8081/sensor/check";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri, String.class);
        if (response != null) {
            EmergencyData.getInstance(response);
        } else {
            EmergencyData.getInstance("");
        }
        //TODO: delete println
        System.out.println(response);
    }
}
