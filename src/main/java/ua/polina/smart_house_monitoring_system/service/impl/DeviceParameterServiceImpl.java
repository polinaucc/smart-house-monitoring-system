package ua.polina.smart_house_monitoring_system.service.impl;

import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.dto.DeviceParameterDto;
import ua.polina.smart_house_monitoring_system.entity.DeviceParameter;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;
import ua.polina.smart_house_monitoring_system.repository.DeviceParameterRepository;
import ua.polina.smart_house_monitoring_system.service.DeviceParameterService;

import java.util.List;

@Service
public class DeviceParameterServiceImpl implements DeviceParameterService {
    DeviceParameterRepository deviceParameterRepository;

    public DeviceParameterServiceImpl(DeviceParameterRepository deviceParameterRepository) {
        this.deviceParameterRepository = deviceParameterRepository;
    }

    @Override
    public List<DeviceParameter> getDeviceParametersByDeviceRoom(DeviceRoom deviceRoom) {
        return deviceParameterRepository.findByRoomDevice(deviceRoom);
    }

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
