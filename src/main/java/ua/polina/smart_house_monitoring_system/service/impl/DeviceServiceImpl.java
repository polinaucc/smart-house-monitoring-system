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

/**
 * The device service implementation.
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    /**
     * The device Repository.
     */
    DeviceRepository deviceRepository;

    /**
     * The device room repository.
     */
    DeviceRoomRepository deviceRoomRepository;

    /**
     * Instantiates a new Device service.
     *
     * @param deviceRepository     the device repository
     * @param deviceRoomRepository the device room repository
     */
    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository,
                             DeviceRoomRepository deviceRoomRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceRoomRepository = deviceRoomRepository;
    }

    /**
     * Saves a device.
     *
     * @param deviceDto the device dto with necessary information for
     *                  device saving
     * @return saved device
     */
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

    /**
     * Saves a device to the certain room.
     *
     * @param deviceDto the device user dto with necessary information for
     *                  device saving
     * @return saved device in the room
     */
    @Override
    public DeviceRoom saveDevice(DeviceUserDto deviceDto, Room room) {
        Device device = deviceRepository.findById(deviceDto.getDeviceId())
                .orElseThrow(() -> new IllegalArgumentException("no.such.device"));
        DeviceRoom deviceRoom = DeviceRoom.builder()
                .room(room)
                .device(device)
                .state(State.OFF)
                .build();

        return deviceRoomRepository.save(deviceRoom);
    }

    /**
     * Gets devices by the room.
     *
     * @param room the room
     * @return the list of devices in the room
     */
    @Override
    public List<DeviceRoom> getDevicesByRoom(Room room) {
        return deviceRoomRepository.findDeviceRoomByRoom(room);
    }

    /**
     * Gets all devices in the system.
     *
     * @return the list of devices
     */
    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    /**
     * Gets device by id.
     *
     * @param id device id
     * @return the device if it exists, otherwise - exception
     * @throws IllegalArgumentException if the device with such id doesn't
     * exist
     */
    @Override
    public Device getDeviceById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no.such.device"));
    }

    /**
     * Gets the list of devices in the room by the room and the device.
     *
     * @param room the room
     * @param device the device
     * @return the list of devices in the room
     */
    @Override
    public List<DeviceRoom> getDeviceRoomByRoomAndDevice(Room room, Device device) {
        return deviceRoomRepository.findDeviceRoomByRoomAndDevice(room, device);
    }

    /**
     * Gets the device in room by its id.
     *
     * @param id the id of device in the room
     * @return device in thee room
     */
    @Override
    public DeviceRoom getDeviceRoomById(Long id) {
        return deviceRoomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no.such.device"));
    }
}
