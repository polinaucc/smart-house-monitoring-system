package ua.polina.smart_house_monitoring_system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.dto.DeviceDto;
import ua.polina.smart_house_monitoring_system.dto.DeviceUserDto;
import ua.polina.smart_house_monitoring_system.entity.Device;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;
import ua.polina.smart_house_monitoring_system.entity.Room;
import ua.polina.smart_house_monitoring_system.entity.State;
import ua.polina.smart_house_monitoring_system.exception.DataExistsException;
import ua.polina.smart_house_monitoring_system.repository.DeviceRepository;
import ua.polina.smart_house_monitoring_system.repository.DeviceRoomRepository;
import ua.polina.smart_house_monitoring_system.service.DeviceService;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    DeviceRepository deviceRepository;
    DeviceRoomRepository deviceRoomRepository;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository,
                             DeviceRoomRepository deviceRoomRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceRoomRepository = deviceRoomRepository;
    }

    @Override
    public Device saveDevice(DeviceDto deviceDto) {
        if (deviceRepository.existsByName(deviceDto.getName())) {
            throw new DataExistsException("device.name.exists");
        }
        Device device = Device.builder()
                .name(deviceDto.getName())
                .build();
        return deviceRepository.save(device);
    }

    @Override
    public DeviceRoom saveDevice(DeviceUserDto deviceDto, Room room) {
        Device device = deviceRepository.findById(deviceDto.getDeviceId())
                .orElseThrow(()->new IllegalArgumentException("no.such.device"));
        DeviceRoom deviceRoom = DeviceRoom.builder()
                .room(room)
                .device(device)
                .state(State.OFF)
                .build();

        return deviceRoomRepository.save(deviceRoom);
    }

    @Override
    public List<DeviceRoom> getDevicesByRoom(Room room) {
        List<DeviceRoom> deviceRoomList = deviceRoomRepository.findDeviceRoomByRoom(room);
//        List<Device> devices = new ArrayList<>();
//        for (DeviceRoom d :
//                deviceRoomList) {
//            devices.add(d.getDevice());
//        }
        return deviceRoomList;
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Device getDeviceById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no.such.device"));
    }

    @Override
    public List<DeviceRoom> getDeviceRoomByRoomAndDevice(Room room, Device device) {
        return deviceRoomRepository.findDeviceRoomByRoomAndDevice(room, device);
    }

    @Override
    public DeviceRoom getDeviceRoomById(Long id) {
        return deviceRoomRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no.such.device"));
    }
}
