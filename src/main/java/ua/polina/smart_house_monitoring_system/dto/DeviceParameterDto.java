package ua.polina.smart_house_monitoring_system.dto;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

/**
 * The type Device parameter dto for device parameters' objects.
 */
@Data
public class DeviceParameterDto {
    String name;
    String description;
    @NumberFormat
    Double minTheoreticalValue;
    @NumberFormat
    Double maxTheoreticalValue;
}
