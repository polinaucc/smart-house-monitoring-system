package ua.polina.smart_house_monitoring_system.service;

import ua.polina.smart_house_monitoring_system.entity.DeviceParameter;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;

import java.util.List;

public interface DeviceParameterService {
    List<DeviceParameter> getDeviceParametersByDeviceRoom(List<DeviceRoom> deviceRooms);
}
