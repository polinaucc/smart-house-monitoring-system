package ua.polina.smart_house_monitoring_system.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * The type Room dto for room object.
 */
@Data
public class RoomDto {
    @NotBlank
    String name;

    @Min(1)
    Double size;
}
