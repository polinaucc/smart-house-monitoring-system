package ua.polina.smart_house_monitoring_system.dto;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Data
public class DeviceParameterDto {
    String name;
    String description;
    @NumberFormat
    Double minTheoreticalValue;
    @NumberFormat
    Double maxTheoreticalValue;
}
