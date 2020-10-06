package ua.polina.smart_house_monitoring_system.service;

import ua.polina.smart_house_monitoring_system.dto.DeviceDto;
import ua.polina.smart_house_monitoring_system.dto.DeviceUserDto;
import ua.polina.smart_house_monitoring_system.entity.Device;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;
import ua.polina.smart_house_monitoring_system.entity.Room;

import java.util.List;

public interface DeviceService {
    Device saveDevice(DeviceDto deviceDto);

    DeviceRoom saveDevice(DeviceUserDto deviceDto, Room room);

    List<DeviceRoom> getDevicesByRoom(Room room);

    List<Device> getAllDevices();

    Device getDeviceById(Long id);

    List<DeviceRoom> getDeviceRoomByRoomAndDevice(Room room, Device device);

    DeviceRoom getDeviceRoomById(Long id);
}
