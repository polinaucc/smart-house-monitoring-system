package ua.polina.smart_house_monitoring_system.service;

import ua.polina.smart_house_monitoring_system.dto.DeviceDto;
import ua.polina.smart_house_monitoring_system.entity.Device;
import ua.polina.smart_house_monitoring_system.entity.Room;

import java.util.List;

public interface DeviceService {
    Device saveDevice(DeviceDto deviceDto);
    List<Device> getDevicesByRoom(Room room);
}
