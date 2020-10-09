package ua.polina.smart_house_monitoring_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The type Smart house monitoring system application.
 */
@SpringBootApplication
@EnableScheduling
public class SmartHouseMonitoringSystemApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
		SpringApplication.run(SmartHouseMonitoringSystemApplication.class, args);
	}

}
