package ua.polina.smart_house_monitoring_system.api;

import lombok.Data;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;

import java.util.List;

@Data
public class DeviceRoomListApi {
    List<DeviceRoom> deviceRoomList;
}
