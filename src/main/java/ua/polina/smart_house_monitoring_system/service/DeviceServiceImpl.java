package ua.polina.smart_house_monitoring_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.dto.DeviceDto;
import ua.polina.smart_house_monitoring_system.entity.Device;
import ua.polina.smart_house_monitoring_system.entity.State;
import ua.polina.smart_house_monitoring_system.repository.DeviceRepository;

@Service
public class DeviceServiceImpl  implements DeviceService{
    DeviceRepository deviceRepository;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Device saveDevice(DeviceDto deviceDto) {
        Device device = Device.builder()
                .name(deviceDto.getName())
                .state(State.OFF)
                .build();
        return deviceRepository.save(device);
    }
}
