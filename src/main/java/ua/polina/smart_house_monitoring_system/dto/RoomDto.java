package ua.polina.smart_house_monitoring_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * The type Room dto for room object.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    @NotBlank
    String name;

    @Min(1)
    Double size;
}
