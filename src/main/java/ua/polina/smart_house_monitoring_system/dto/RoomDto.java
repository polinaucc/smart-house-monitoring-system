package ua.polina.smart_house_monitoring_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

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
    @NotBlank(message = "{name.not.blank}")
    String name;

    @Min(value = 1, message = "{more.than.one}")
    @NumberFormat
    Double roomSize;
}
