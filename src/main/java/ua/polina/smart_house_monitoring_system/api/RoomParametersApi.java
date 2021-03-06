package ua.polina.smart_house_monitoring_system.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.polina.smart_house_monitoring_system.dto.RoomParameterDto;
import ua.polina.smart_house_monitoring_system.entity.Room;

/**
 * The type Room parameters api to send room parameters and room.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomParametersApi {
    private RoomParameterDto roomParameterDto;
    private Room room;
}
