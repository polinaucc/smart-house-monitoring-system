package ua.polina.smart_house_monitoring_system.dto;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * The dto for room parameter object.
 */
@Data
public class RoomParameterDto {
    @NumberFormat
    private Double temperature;

    @NumberFormat
    @Min(value = 0, message = "min.value.error")
    @Max(value = 100, message = "max.value.error")
    private Double humidity;

    @Min(value = 0, message = "min.value.error")
    @Max(value = 100, message = "max.value.error")
    private Double smokeLevel;

    @Min(value = 0, message = "min.value.error")
    @Max(value = 100, message = "max.value.error")
    private Double waterLevel;
}
