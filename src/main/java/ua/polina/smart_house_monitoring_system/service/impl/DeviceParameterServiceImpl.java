package ua.polina.smart_house_monitoring_system.service.impl;

import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.dto.DeviceParameterDto;
import ua.polina.smart_house_monitoring_system.entity.DeviceParameter;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;
import ua.polina.smart_house_monitoring_system.repository.DeviceParameterRepository;
import ua.polina.smart_house_monitoring_system.service.DeviceParameterService;

import java.util.List;

/**
 * Device Parameter Service implementation.
 */
@Service
public class DeviceParameterServiceImpl implements DeviceParameterService {
    /**
     * DeviceParameter repository.
     */
    DeviceParameterRepository deviceParameterRepository;

    /**
     * Instantiates a new Device parameter service.
     *
     * @param deviceParameterRepository the device parameter repository
     */
    public DeviceParameterServiceImpl(DeviceParameterRepository deviceParameterRepository) {
        this.deviceParameterRepository = deviceParameterRepository;
    }

    /**
     * Gets device parameters by device in room.
     *
     * @param deviceRoom the device in room
     * @return the list of device parameters
     */
    @Override
    public List<DeviceParameter> getDeviceParametersByDeviceRoom(DeviceRoom deviceRoom) {
        return deviceParameterRepository.findByRoomDevice(deviceRoom);
    }

    /**
     * Saves the device parameter.
     *
     * @param deviceParameterDto the device parameter dto
     * @param deviceRoom the device in room
     * @return the device parameter
     */
    @Override
    public DeviceParameter saveDeviceParameter(DeviceParameterDto deviceParameterDto, DeviceRoom deviceRoom) {
        DeviceParameter deviceParameter = DeviceParameter.builder()
                .roomDevice(deviceRoom)
                .name(deviceParameterDto.getName())
                .description(deviceParameterDto.getDescription())
                .minTheoreticalValue(deviceParameterDto.getMinTheoreticalValue())
                .maxTheoreticalValue(deviceParameterDto.getMaxTheoreticalValue())
                .build();
        return deviceParameterRepository.save(deviceParameter);
    }
}
