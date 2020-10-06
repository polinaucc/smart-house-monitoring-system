package ua.polina.smart_house_monitoring_system.service;

import ua.polina.smart_house_monitoring_system.dto.DeviceParameterDto;
import ua.polina.smart_house_monitoring_system.entity.DeviceParameter;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;

import java.util.List;

public interface DeviceParameterService {
    List<DeviceParameter> getDeviceParametersByDeviceRoom(DeviceRoom deviceRoom);
    DeviceParameter saveDeviceParameter(DeviceParameterDto deviceParameterDto, DeviceRoom deviceRoom);
}
